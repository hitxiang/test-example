package com.playfish.palindrome.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.playfish.palindrome.dao.UserDao;
import com.playfish.palindrome.dao.impl.UserDaoLocalImpl;
import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class PalindromeGame {
	private final static int NUM_IN_TOP_RANK = 5;
	private final static Logger logger = Logger.getLogger(PalindromeGame.class);
	
	private static enum RankType {HIGHEST, TOTAL}

	private final static  Object[] lockArray = new Object [] {new Object(), new Object()};
	private final static  List<Queue<Score>> rankQueueList = new ArrayList<Queue<Score>>(2);
	static {
		rankQueueList.add(new PriorityQueue<Score>(NUM_IN_TOP_RANK + 1));
		rankQueueList.add(new PriorityQueue<Score>(NUM_IN_TOP_RANK + 1));		
	}



	private final static UserDao userDao = UserDaoLocalImpl.getInstance();

	private PalindromeGame() {
	}
	
	public static User addUser(String uuid) {		
		User u = userDao.add(uuid);
        return u;
	}
	
	public static User getUser(String uuid) {
		return userDao.get(uuid);
	}

	public static User registerUser(String uuid, String name) {
		User u = userDao.register(uuid, name);
		checkAndpdateQueue(RankType.HIGHEST, u);
		checkAndpdateQueue(RankType.TOTAL, u);			
		return u;
	}

	public static void update(User u, int score) {
		boolean highestUpdted = u.update(score);
		if (u.isRegisted()) {
			if (highestUpdted) {
				checkAndpdateQueue(RankType.HIGHEST, u);				
			}
			checkAndpdateQueue(RankType.TOTAL, u);		
		}
	}

	public static Score[] getHighestScoreRanks() {
		return getTopScoreArray(RankType.HIGHEST);
	}

	public static Score[] getTotalScoreRanks() {
		return getTopScoreArray(RankType.TOTAL);
	}
	
	/**
	 * Only for unit test
	 * */
	public static void clear() {
		for (Queue<Score> queue: rankQueueList) {
			queue.clear();
		}
		userDao.clear();	
	}

	private static Score[] getTopScoreArray(RankType type) {
		Score[] result;
		Queue<Score> queue = rankQueueList.get(type.ordinal());
		Object lock = lockArray[type.ordinal()];

		synchronized(lock) {
			result = (Score[]) queue.toArray(new Score[0]);
		}
		Arrays.sort(result, Collections.reverseOrder());

		if (result.length > NUM_IN_TOP_RANK) { // impossible in normal case
			logger.error(queue + " : more than " + NUM_IN_TOP_RANK + " records => [" + result.length + "]");
			Score[] result2 = new Score[NUM_IN_TOP_RANK];
			System.arraycopy(result, 0, result2, 0, NUM_IN_TOP_RANK);
			return result2;
		}

		return result;
	}

	private static void checkAndpdateQueue(RankType type, User u) {
		Queue<Score> queue = rankQueueList.get(type.ordinal());
		long score;
		switch (type) {
		case HIGHEST:
			score = u.getHighestScore();
			break;
		case TOTAL:
			score = u.getTotalScore();
			break;
		default:
			logger.error("Wrong rank type");
			return;
		}
		
		synchronized (lockArray[type.ordinal()]) {
			if (queue.size() >= NUM_IN_TOP_RANK) {
				Score minScore = queue.peek(); 				
				Score newScore = new Score(u.getId(), u.getName(), score);
				
				// Update only when the new score is greater than the smallest score in queue
				if (minScore.getScore() < score) {
					updateQueue(queue, newScore, true);
				}
			} else {				
				Score newScore = new Score(u.getId(), u.getName(), score);
				updateQueue(queue, newScore, false);
				  
			}
		}
	}

	private static void updateQueue(Queue<Score> queue, Score newScore, boolean isToDeleteOld) {
		if (queue.contains(newScore)) { 
			// Update when the user exists
			for (Score s : queue) {
				if (s.equals(newScore)) {
					if (logger.isDebugEnabled()) {
						logger.debug(queue + " ==> update Score: " + s + " ==> " + newScore.getScore());								
					}

					s.setScore(newScore.getScore());
				}
			}
		} else {
			// Remove the smallest one, add the new one
			if (isToDeleteOld) {
				Score oldScore = queue.poll();	
				if (logger.isDebugEnabled()) {
					logger.debug(queue + " ==> delete Score: " + oldScore);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug(queue + " <== add    Score: " + newScore);
			}
			queue.add(newScore);

		}
	}

}
