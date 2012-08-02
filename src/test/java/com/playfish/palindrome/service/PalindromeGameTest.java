package com.playfish.palindrome.service;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import com.playfish.palindrome.model.*;

public class PalindromeGameTest {
	@Test
	public void testOneUser() {
		User u1 = processOneUserWithoutRegister();
		assertEquals(0, PalindromeGame.getTopHighestScoreList().length);
		assertEquals(0, PalindromeGame.getTopTotalScoreList().length);
		
		User u2 = processOneUser();
		assertEquals(u2.getHighestScore(), PalindromeGame.getTopHighestScoreList()[0].getScore());
		assertEquals(u2.getTotalScore(), PalindromeGame.getTopTotalScoreList()[0].getScore());
		
	}
	
	@Test
	public void testMultipleUser() {

		User[] userArray = new User[3];
		long[] highArray = new long[3];
		long[] totalArray = new long[3];
		
		long[] actualHighArray = new long[3];
		long[] actualTotalArray = new long[3];
		
		
		userArray[0] = processOneUser();
		userArray[1] = processOneUser();
		userArray[2] = processOneUser();
		Score[] highScores = PalindromeGame.getTopHighestScoreList();
		Score[] totalScores = PalindromeGame.getTopTotalScoreList();
        for (int i=0; i< userArray.length; i++) {
        	highArray[i] = userArray[i].getHighestScore();
        	totalArray[i] = userArray[i].getTotalScore();
        	
        	actualHighArray[i] = highScores[i].getScore();
        	actualTotalArray[i] = totalScores[i].getScore();
        }
		
        Arrays.sort(totalArray);
        Arrays.sort(highArray);

		assertArrayEquals(totalArray, actualTotalArray);
		assertArrayEquals(highArray, actualHighArray);
		
	}
	
	
	
	private User processOneUserWithoutRegister() {
        Random rand = new Random(new Date().getTime());
		int[] scores = new int[]{
				rand.nextInt(200),
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200)
		};
		String uuid1 = UUID.randomUUID().toString();
		
		User u1 = PalindromeGame.addUser(uuid1);
		
		int max = Integer.MIN_VALUE;
		long total = 0;
		for (int score : scores) {
			total += score;
			if ( score > max) max = score;
			PalindromeGame.update(u1, score);
		}
		assertEquals(max, u1.getHighestScore());
		assertEquals(total ,u1.getTotalScore());
		
		return u1;
				
	}
	
	private User processOneUser() {
        Random rand = new Random(new Date().getTime());
		int[] scores = new int[]{
				rand.nextInt(200),
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200), 
				rand.nextInt(200)
		};
		String uuid1 = UUID.randomUUID().toString();
		
		User u1 = PalindromeGame.addUser(uuid1);
		
		int max = Integer.MIN_VALUE;
		long total = 0;
		for (int score : scores) {
			total += score;
			if ( score > max) max = score;
			PalindromeGame.update(u1, score);
		}
		assertEquals(max, u1.getHighestScore());
		assertEquals(total ,u1.getTotalScore());

		
		u1 = PalindromeGame.registerUser(u1.getId(), "test_" + u1.getId());
		
		return u1;
				
	}
	


}
