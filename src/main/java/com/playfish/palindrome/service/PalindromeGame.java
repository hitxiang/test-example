package com.playfish.palindrome.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.playfish.palindrome.dao.ScoreDao;
import com.playfish.palindrome.dao.UserDao;
import com.playfish.palindrome.dao.impl.UserDaoLocalImpl;
import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class PalindromeGame {

  private final static Logger logger = Logger.getLogger(PalindromeGame.class);

  private final static UserDao userDao = UserDaoLocalImpl.getInstance();

  private final static ScoreDao highScoreDao = new ScoreDao();
  private final static ScoreDao totalScoreDao = new ScoreDao();

  // private final static ExecutorService executorPool =
// Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
  private final static ExecutorService executorPool = Executors.newCachedThreadPool();

  private PalindromeGame() {}

  public static User addUser(String uuid) {
    User u = userDao.add(uuid);
    return u;
  }

  public static User getUser(String uuid) {
    return userDao.get(uuid);
  }

  public static User registerUser(String uuid, String name) {
    final User u = userDao.register(uuid, name);

    concurrentUpdate(u, true);

    return u;
  }

  public static void updateRanks(final User u, final int score) {
    boolean highestUpdted = u.update(score);
    if (u.isRegisted()) {
      concurrentUpdate(u, highestUpdted);
    }
  }

  public static Score[] getHighestScoreRanks() {
    return highScoreDao.getScores();
  }

  public static Score[] getTotalScoreRanks() {
    return totalScoreDao.getScores();
  }

  /**
   * Only for unit test
   * */
  public static void clear() {
    highScoreDao.clear();
    totalScoreDao.clear();
    userDao.clear();
  }


  private static void concurrentUpdate(final User u, boolean isToUpdateHighRank) {
    final List<Callable<Boolean>> partitions = new ArrayList<Callable<Boolean>>();

    if (isToUpdateHighRank) {
      partitions.add(new Callable<Boolean>() {
        public Boolean call() throws Exception {
          return highScoreDao.checkAndUpdateQueue(u, u.getHighestScore());
        }
      });
    }

    partitions.add(new Callable<Boolean>() {
      public Boolean call() throws Exception {
        return totalScoreDao.checkAndUpdateQueue(u, u.getTotalScore());
      }
    });


    try {
      executorPool.invokeAll(partitions, 10, TimeUnit.SECONDS);
    } catch (InterruptedException ex) {
      logger.error("Cannot update scrore ranks:" + ex);
    }

  }

  private static void sequenceUpdate(final User u, boolean isToUpdateHighRank) {
    if (isToUpdateHighRank) {
      highScoreDao.checkAndUpdateQueue(u, u.getHighestScore());
    }

    totalScoreDao.checkAndUpdateQueue(u, u.getTotalScore());

  }


}
