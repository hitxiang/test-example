package com.playfish.palindrome.model;

import static org.junit.Assert.*;

import java.util.AbstractQueue;
import java.util.PriorityQueue;
import java.util.UUID;

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
  

}
