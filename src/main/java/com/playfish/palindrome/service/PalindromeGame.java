package com.playfish.palindrome.service;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class PalindromeGame {
	private final static int NUM_IN_TOP_RANK = 5;
	private final static Logger logger = Logger.getLogger(PalindromeGame.class);

	private static Object highestScoreLock = new Object();
	private static AbstractQueue<Score> highestScoreQueue = new PriorityQueue<Score>(
			NUM_IN_TOP_RANK + 1, new Score.ScoreComparator());

	private static Object totalScoreLock = new Object();
	private static AbstractQueue<Score> totalScoreQueue = new PriorityQueue<Score>(
			NUM_IN_TOP_RANK + 1, new Score.ScoreComparator());

	private static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<String, User>();

	private PalindromeGame() {
	}
	
	public static User addUser(String uuid) {		
		User u = new User(uuid);
		userMap.put(uuid, u);
        return u;
	}

	public static User registerUser(String uuid, String name) {
		User u = userMap.get(uuid);
		u.setName(name);
		u.setRegisted(true);
		
		updateQueue(highestScoreLock, highestScoreQueue, u, u.getHighestScore());
		updateQueue(totalScoreLock, totalScoreQueue, u, u.getTotalScore());			
		return u;
	}

	public static void update(User u, int score) {
		u.update(score);
		if (u.isRegisted()) {
			updateQueue(highestScoreLock, highestScoreQueue, u, u.getHighestScore());
			updateQueue(totalScoreLock, totalScoreQueue, u, u.getTotalScore());		
		}
	}

	public static Score[] getTopHighestScoreList() {
		return getTopScoreArray(highestScoreQueue);
	}

	public static Score[] getTopTotalScoreList() {
		return getTopScoreArray(totalScoreQueue);
	}

	private static Score[] getTopScoreArray(AbstractQueue<Score> queue) {
		Score[] result = (Score[]) queue.toArray(new Score[0]);
		Arrays.sort(result, new Score.ScoreComparator());

		if (result.length > NUM_IN_TOP_RANK) { // impossible in normal case
			Score[] result2 = new Score[NUM_IN_TOP_RANK];
			System.arraycopy(result, 0, result2, 0, NUM_IN_TOP_RANK);
			return result2;
		}

		return result;
	}

	private static void updateQueue(Object lock, AbstractQueue<Score> queue,
			User u, long score) {
		synchronized (lock) {
			if (queue.size() >= 5) {
				Score minScore = queue.peek();
				Score newScore = new Score(u.getId(), u.getName(), score);
				if (minScore.getScore() < score) {
					if (queue.contains(newScore)) {
						// remove the old one, add the new score;
						for (Score s : queue) {
							if (s.equals(newScore)) {
								logger.debug(queue + " ==> delete Score: " + s);
								logger.debug(queue + " <== add    Score: " + newScore);
								s.setScore(newScore.getScore());
							}
						}
					} else {
						Score oldScore = queue.poll();
						queue.add(newScore);
						if (logger.isDebugEnabled()) {
							logger.debug(queue + " ==> delete Score: " + oldScore);
							logger.debug(queue + " <== add    Score: " + newScore);
						}
					}
				}
			} else {
				Score newScore = new Score(u.getId(), u.getName(), score);
				queue.add(new Score(u.getId(), u.getName(), score));
				if (logger.isDebugEnabled()) {
					logger.debug(queue + " <== add    Score: " + newScore);
				}
				  
			}
		}
	}

}
