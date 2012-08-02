package com.playfish.palindrome.service;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;

public class PalindromeGame {
  private final static int NUM_IN_TOP_RANK = 5;
  
  private static Object highestScoreLock;
  private static AbstractQueue<Score> highestScoreQueue = 
      new PriorityQueue<Score>(NUM_IN_TOP_RANK + 1, new Score.ScoreComparator());
  
  private static Object totalScoreLock;
  private static AbstractQueue<Score> totalScoreQueue = 
      new PriorityQueue<Score>(NUM_IN_TOP_RANK + 1, new Score.ScoreComparator());
  
  private static ConcurrentHashMap<String, User> unregistedUserMap = new ConcurrentHashMap<String, User>();
  private static ConcurrentHashMap<String, User> registedUserMap = new ConcurrentHashMap<String, User>();
  

  private PalindromeGame(){}
  
  public static void updateUser(String uuid, int score) {
    User u = unregistedUserMap.get(uuid);
    u.update(score); 
  }
  
  public static void updateRanks(User u) {
    updateQueue(highestScoreLock, highestScoreQueue, u, u.getHighestScore());
    updateQueue(totalScoreLock, totalScoreQueue, u, u.getTotalScore());
  }
  

  
  public static User registerUser(String uuid, String name){
    User u = unregistedUserMap.remove(uuid);
    u.setName(name);
    registedUserMap.put(uuid, u);
    return u;
  }
  
  public static Score[] getTopHighestScoreList(){
    return getTopScoreArray(highestScoreQueue);
  }
  
  public static Score[] getTopTotalScoreList() {
    return getTopScoreArray(totalScoreQueue);
  }
  
  private static Score[] getTopScoreArray(AbstractQueue<Score> queue) {
    Score[] result = (Score[])queue.toArray(new Score[0]); 
    Arrays.sort(result, new Score.ScoreComparator()) ;
    
    if (result.length > NUM_IN_TOP_RANK) { // impossible in normal case
      Score[] result2 = new Score[NUM_IN_TOP_RANK];
      System.arraycopy(result, 0, result2, 0, NUM_IN_TOP_RANK);
      return result2;
    }
    
    return result;    
  }
  
  private static void updateQueue(Object lock, AbstractQueue<Score> queue, User u, long score) {
    synchronized(lock) {
      if (queue.size() >= 5 ) {
        Score minScore = queue.peek();
        Score newScore = new Score(u.getId(), u.getName(), u.getHighestScore());
        if (minScore.getScore() < score) {
          if (queue.contains(newScore)) {
            // remove the old one, add the new score;
            queue.remove(newScore);
            queue.add(newScore);
//            for (Score s: queue) {
//              if (s.equals(newScore)) {
//                s.setScore(newScore.getScore());
//              }
//            }
          } else {
            queue.poll();
            queue.add(newScore);
          }
        }
      } else {
        queue.add(new Score(u.getId(), u.getName(), u.getHighestScore()));
      }
    }
  }
  
  
}
