package com.playfish.palindrome.model;

import java.util.UUID;

public class User {

	private UUID id;
	private String name;
	private long totalScore = 0;
	private long highestScore = 0;
	
	public User(String name) {
	  id = UUID.randomUUID();	
	}
	
	public String getId() {
		return id.toString();
	}

	public String getName() {
		return name;
	}


	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public long getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(long highestScore) {
		this.highestScore = highestScore;
	}
}
