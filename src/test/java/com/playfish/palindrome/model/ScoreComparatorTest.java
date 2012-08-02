package com.playfish.palindrome.model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class ScoreComparatorTest {
  @Test
  public void testOrderInQueue(){
    AbstractQueue<Score> highestScoreQueue = 
        new PriorityQueue<Score>(5, new Score.ScoreComparator());
    highestScoreQueue.add(new Score(UUID.randomUUID().toString(), "name101", 101));
    highestScoreQueue.add(new Score(UUID.randomUUID().toString(), "name1101", 1101));
    highestScoreQueue.add(new Score(UUID.randomUUID().toString(), "name11", 11));
    highestScoreQueue.add(new Score(UUID.randomUUID().toString(), "name9", 9));
    highestScoreQueue.add(new Score(UUID.randomUUID().toString(), "name99", 99));
    
    long[] result = new long[5];
    long[] expecteds = new long[]{9,11,99,101,1101};
    for (int i = 0; i < 5; i++) {
      result[i] = highestScoreQueue.poll().getScore(); 
    }
    assertArrayEquals(expecteds, result);
    
  }
  
  @Test
  public void testOrderInSort(){
    List<Score> highestScoreList = 
        new ArrayList<Score>(5);
    highestScoreList.add(new Score(UUID.randomUUID().toString(), "name101", 101));
    highestScoreList.add(new Score(UUID.randomUUID().toString(), "name1101", 1101));
    highestScoreList.add(new Score(UUID.randomUUID().toString(), "name11", 11));
    highestScoreList.add(new Score(UUID.randomUUID().toString(), "name9", 9));
    highestScoreList.add(new Score(UUID.randomUUID().toString(), "name99", 99));
    
    Score[] highestArray = highestScoreList.toArray(new Score[0]);
    Arrays.sort(highestArray, new Score.ScoreComparator());
    
    long[] result = new long[5];
    long[] expecteds = new long[]{9,11,99,101,1101};
    for (int i = 0; i < 5; i++) {
      result[i] = highestArray[i].getScore(); 
    }
    assertArrayEquals(expecteds, result);
    
    Arrays.sort(highestArray);
    expecteds = new long[]{1101,101,99,11,9};
    for (int i = 0; i < 5; i++) {
      result[i] = highestArray[i].getScore(); 
    }
    assertArrayEquals(expecteds, result);   
  }
  

}
