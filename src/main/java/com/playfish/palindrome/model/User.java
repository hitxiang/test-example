package com.playfish.palindrome.model;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class User {

        final public static String IDENTIFY_TAG = "PF_UUID";
        final public static String IS_NEW_TAG = "PF_IS_NEW";
	private UUID id;
	private String name;

        private long totalScore = 0;
	private int highestScore = 0;

        private String lastInput;
        private Date lastActive;
	
	public User() {
	  this.id = UUID.randomUUID();
	  this.lastActive = new Date();
	}
	
	public String getId() {
		return id.toString();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
	       this.name = name;
	}


	public long getTotalScore() {
		return totalScore;
	}

	public int getHighestScore() {
		return highestScore;
	}
	
	public void setLastInput(String lastInput) {
	        this.lastInput = lastInput;
	}
	
	
	public boolean isSameWithLast(String str) {
	       return (lastInput!=null && lastInput.equalsIgnoreCase(str));
	}
	
	public void update(int score) {
	  this.lastActive = new Date();
	  this.totalScore += score;
	  if (score > this.highestScore) {
	    this.highestScore = score;
	  }
	  
	}

}
