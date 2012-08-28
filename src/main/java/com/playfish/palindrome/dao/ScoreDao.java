package com.playfish.palindrome.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class ScoreDao {
  private final static int NUM_IN_TOP_RANK = 5;
  private final static Logger logger = Logger.getLogger(ScoreDao.class);

  private final Queue<Score> queue = new PriorityQueue<Score>(NUM_IN_TOP_RANK);
  private final Object lock = new Object();

  public Score[] getScores() {
    Score[] result;
    synchronized (lock) {
      result = (Score[]) queue.toArray(new Score[0]);
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

  public boolean checkAndUpdateQueue(User u, long score) {
    synchronized (lock) {
      if (queue.size() >= NUM_IN_TOP_RANK) {
        Score minScore = queue.peek();
        Score newScore = new Score(u.getId(), u.getName(), score);

        // Update only when the new score is greater than the smallest score in queue
        if (minScore.getScore() < score) {
          updateQueue(newScore, true);
        }
      } else {
        Score newScore = new Score(u.getId(), u.getName(), score);
        updateQueue(newScore, false);

      }
    }
    
    return true;
  }
  
  public void clear() {
    synchronized (lock) {
      queue.clear();
    }
  }


  private void updateQueue(Score newScore, boolean isToDeleteOld) {
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
