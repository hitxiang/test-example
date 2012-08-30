package com.playfish.palindrome.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class ScoreDao {
  private final static int NUM_IN_TOP_RANK = 5;
  private final static Logger logger = Logger.getLogger(ScoreDao.class);

  private final Queue<Score> queue = new PriorityQueue<Score>(NUM_IN_TOP_RANK);
  private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private final Lock readLock = readWriteLock.readLock();
  private final Lock writeLock = readWriteLock.writeLock();
  

  public Score[] getScores() {
    Score[] result;
    
    readLock.lock();
    try {
      result = queue.toArray(new Score[0]);
    } finally {
      readLock.unlock();
    }

    Arrays.sort(result, Collections.reverseOrder());

    if (result.length > NUM_IN_TOP_RANK) { // impossible in normal case
      logger.error(queue + " : more than " + NUM_IN_TOP_RANK + " records => [" + result.length
          + "]");
      Score[] result2 = new Score[NUM_IN_TOP_RANK];
      System.arraycopy(result, 0, result2, 0, NUM_IN_TOP_RANK);
      return result2;
    }

    return result;
  }

  public boolean checkAndUpdateQueue(User u, Score.TYPE type) {
	long score = 0;
	switch (type) {
	case HIGH:
		score = u.getHighestScore();
		break;
	case TOTAL:
		score = u.getTotalScore();
		break;
	default:
		logger.error("Wrong score type");
	}
    Score newScore = new Score(type, u.getId(), u.getName(), score);
    
    writeLock.lock();
    try {
      if (queue.size() >= NUM_IN_TOP_RANK) {
        Score minScore = queue.peek();

        // Update only when the new score is greater than the smallest score in queue
        if (minScore.getScore() < score) {
          updateQueue(newScore, true);
        }
      } else {
        updateQueue(newScore, false);
      }
    } finally {
      writeLock.unlock();
    }

    return true;
  }

  public void clear() {
    
    writeLock.lock();
    try {
      queue.clear();
    } finally {
      writeLock.unlock();
    }
  }


  private void updateQueue(Score newScore, boolean isToDeleteOld) {
	Comparator<Score> comparator = new Score.IdComparator();
	Score[] scoreArr = queue.toArray(new Score[0]);
	Arrays.sort(scoreArr, comparator);
	int index = Arrays.binarySearch(scoreArr, newScore, comparator);
    if (index >= 0) {
      // Update when the user exists
      Score oldScore = scoreArr[index];
      if (logger.isDebugEnabled()) {
        logger.debug(queue + " ==> update Score: " + oldScore + " ==> " + newScore.getScore());
      }

      oldScore.setScore(newScore.getScore());

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
