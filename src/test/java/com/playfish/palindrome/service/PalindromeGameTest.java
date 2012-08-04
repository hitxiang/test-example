package com.playfish.palindrome.service;

import static org.junit.Assert.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.playfish.palindrome.model.*;

public class PalindromeGameTest {
	private static Random rand = new Random(new Date().getTime());
	private final static int NUM_IN_TOP_RANK = 5;
	
	@Before
	public void setup() {
		PalindromeGame.clear();
		System.out.println(">>>>>> ========= new start ==========");
	}
	
	@Test
	public void testOneUser_WithoutRegister() {
		processOneUserWithoutRegister();
		assertEquals(0, PalindromeGame.getHighestScoreRanks().length);
		assertEquals(0, PalindromeGame.getTotalScoreRanks().length);
	}
	
	@Test
	public void testOneUser_WithRegister() {		
		User u2 = processOneUser();
		assertEquals(u2.getHighestScore(), PalindromeGame.getHighestScoreRanks()[0].getScore());
		assertEquals(u2.getTotalScore(), PalindromeGame.getTotalScoreRanks()[0].getScore());
		
	}
	
	@Test
	public void testMultipleUser_less5() {

		User[] userArray = new User[4];
		long[] highArray = new long[4];
		long[] totalArray = new long[4];
		
		long[] actualHighArray = new long[4];
		long[] actualTotalArray = new long[4];
		
		
		userArray[0] = processOneUser();
		userArray[1] = processOneUser();
		userArray[2] = processOneUser();
		userArray[3] = processOneUser();
		Score[] highScores = PalindromeGame.getHighestScoreRanks();
		Score[] totalScores = PalindromeGame.getTotalScoreRanks();
        for (int i=0; i< userArray.length; i++) {
    		System.out.println(userArray[i]);
        	highArray[i] = userArray[i].getHighestScore();
        	totalArray[i] = userArray[i].getTotalScore();
        	
        	actualHighArray[i] = highScores[i].getScore();
        	actualTotalArray[i] = totalScores[i].getScore();
        }
		
        Arrays.sort(totalArray);
        reverseOrder(totalArray);
        
        Arrays.sort(highArray);
        reverseOrder(highArray);
        
        System.out.print("expected totolArray:");
        System.out.println(Arrays.toString(totalArray));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualTotalArray));
        
        System.out.print("expected highArray:");
        System.out.println(Arrays.toString(highArray));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualHighArray));

		assertArrayEquals(totalArray, actualTotalArray);
		assertArrayEquals(highArray, actualHighArray);
		
	}
	
	
	@Test
	public void testMultipleUser_great5() {

		User[] userArray = new User[7];
		long[] highArray = new long[7];
		long[] totalArray = new long[7];
		
		long[] actualHighArray = new long[NUM_IN_TOP_RANK];
		long[] actualTotalArray = new long[NUM_IN_TOP_RANK];
		
		
		userArray[0] = processOneUser();
		userArray[1] = processOneUser();
		userArray[2] = processOneUser();
		userArray[3] = processOneUser();
		userArray[4] = processOneUser();
		userArray[5] = processOneUser();
		userArray[6] = processOneUser();
		Score[] highScores = PalindromeGame.getHighestScoreRanks();
		Score[] totalScores = PalindromeGame.getTotalScoreRanks();
        for (int i=0; i< userArray.length; i++) {
    		System.out.println(userArray[i]);
        	highArray[i] = userArray[i].getHighestScore();
        	totalArray[i] = userArray[i].getTotalScore();
        	
        	if ( i< NUM_IN_TOP_RANK) {
            	actualHighArray[i] = highScores[i].getScore();
            	actualTotalArray[i] = totalScores[i].getScore();       		
        	}

        }
		
        Arrays.sort(totalArray);
        reverseOrder(totalArray);
		long[] totalArray2 = new long[NUM_IN_TOP_RANK];
		System.arraycopy(totalArray, 0, totalArray2, 0, NUM_IN_TOP_RANK);
        
        Arrays.sort(highArray);
        reverseOrder(highArray);
		long[] highArray2 = new long[NUM_IN_TOP_RANK];
		System.arraycopy(highArray, 0, highArray2, 0, NUM_IN_TOP_RANK);
        
        System.out.print("expected totolArray:");
        System.out.println(Arrays.toString(totalArray2));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualTotalArray));
        
        System.out.print("expected highArray:");
        System.out.println(Arrays.toString(highArray2));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualHighArray));

		assertArrayEquals(totalArray2, actualTotalArray);
		assertArrayEquals(highArray2, actualHighArray);
		
	}
	
	@Test
	public void testMultipleUser_multithread() {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_IN_TOP_RANK * 10);
		List<Future<User>> list = new ArrayList<Future<User>>();
		for (int i = 0; i < 20000 * NUM_IN_TOP_RANK; i++) {
			Callable<User> worker = new TestCallable();
			Future<User> u = executor.submit(worker);
			list.add(u);
		}
		
		AbstractQueue<Score> highestScoreQueue = 
		        new PriorityQueue<Score>(5);
		AbstractQueue<Score> totalScoreQueue = 
		        new PriorityQueue<Score>(5);
                User u;
		// Now retrieve the result
		for (Future<User> future : list) {
			try {
				u = future.get();
				highestScoreQueue.add(new Score(u.getId(), u.getName(), u.getHighestScore()));
				if (highestScoreQueue.size() > 5) {
					highestScoreQueue.poll();
				}
				
				totalScoreQueue.add(new Score(u.getId(), u.getName(), u.getTotalScore()));
				if (totalScoreQueue.size() > 5) {
					totalScoreQueue.poll();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		

		
		Score[] highScores = PalindromeGame.getHighestScoreRanks();
		Score[] totalScores = PalindromeGame.getTotalScoreRanks();
		
		Score[] expectedHighScores = (Score[]) highestScoreQueue.toArray(new Score[0]);
		Arrays.sort(expectedHighScores, Collections.reverseOrder());
		Score[] expectedTotalScores = (Score[]) totalScoreQueue.toArray(new Score[0]);
		Arrays.sort(expectedTotalScores, Collections.reverseOrder());
		
		long[] expectedHighArray = new long[NUM_IN_TOP_RANK];
		long[] expectedTotalArray = new long[NUM_IN_TOP_RANK];
		
		long[] actualHighArray = new long[NUM_IN_TOP_RANK];
		long[] actualTotalArray = new long[NUM_IN_TOP_RANK];
		for (int i=0; i < NUM_IN_TOP_RANK; i++) {
			expectedHighArray[i] = expectedHighScores[i].getScore();
			expectedTotalArray[i] = expectedTotalScores[i].getScore();
			
			actualHighArray[i] = highScores[i].getScore();
			actualTotalArray[i] = totalScores[i].getScore();
		}

        System.out.print("expected totolArray:");
        System.out.println(Arrays.toString(expectedTotalArray));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualTotalArray));
        
        System.out.print("expected highArray:");
        System.out.println(Arrays.toString(expectedHighArray));
        System.out.print("actual   totolArray:");
        System.out.println(Arrays.toString(actualHighArray));
        
		assertArrayEquals(expectedHighArray, actualHighArray);
		assertArrayEquals(expectedTotalArray, actualTotalArray);	
	}
	
	class TestCallable implements Callable<User> {
		@Override
		public User call() throws Exception {
			return processOneUser();
		}
		
		

	} 
	
	private User processOneUserWithoutRegister() {
		int[] scores = new int[]{
				rand.nextInt(200),
				rand.nextInt(100), 
				rand.nextInt(200), 
				rand.nextInt(300), 
				rand.nextInt(30), 
				rand.nextInt(20), 
				rand.nextInt(25)
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
        
		int[] scores = new int[]{
				rand.nextInt(10) + rand.nextInt(20),
				rand.nextInt(20) + rand.nextInt(30), 
				rand.nextInt(30) + rand.nextInt(40), 
				rand.nextInt(40) + rand.nextInt(50), 
				rand.nextInt(5) + rand.nextInt(15), 
				rand.nextInt(100) + rand.nextInt(5), 
				rand.nextInt(1000) + rand.nextInt(5)
		};
		String uuid1 = UUID.randomUUID().toString();
		
		User u1 = PalindromeGame.addUser(uuid1);
		
		int max = Integer.MIN_VALUE;
		long total = 0;
		for (int score : scores) {
			System.out.println(u1.getId() + " : " + score);
			total += score;
			if ( score > max) max = score;
			PalindromeGame.update(u1, score);
		}
		assertEquals(max, u1.getHighestScore());
		assertEquals(total ,u1.getTotalScore());

		
		u1 = PalindromeGame.registerUser(u1.getId(), "test_" + u1.getId());
		return u1;
				
	}

	
    private static void reverseOrder(long[] arr) {
    	int len = arr.length;
    	long temp;
    	for( int i = 0; i < len/2; ++i ) 
    	{ 
    	  temp = arr[i]; 
    	  arr[i] = arr[len - i - 1]; 
    	  arr[len - i - 1] = temp; 
    	}
    }


}
