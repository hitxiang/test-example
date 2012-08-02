package com.playfish.palindrome.model;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -6621015387677942871L;
	
	final public static String IDENTIFY_TAG = "PF_UUID";
	final public static String IS_NEW_TAG = "PF_IS_NEW";
	private String id;
	private String name;
	private boolean registed;

	private long totalScore = 0;
	private int highestScore = 0;

	private String lastInput;
	private Date lastActive;

	public User(String id) {
		this.id = id;
		this.registed = false;
		this.lastActive = new Date();
	}
	
	public User() {
		this(UUID.randomUUID().toString());
	}

	public String getId() {
		return id;
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

	public boolean isRegisted() {
		return registed;
	}

	public void setRegisted(boolean registed) {
		this.registed = registed;
	}

	public boolean isSameWithLast(String str) {
		return (lastInput != null && lastInput.equalsIgnoreCase(str));
	}

	public void update(int score) {
		this.lastActive = new Date();
		this.totalScore += score;
		if (score > this.highestScore) {
			this.highestScore = score;
		}

	}

}
