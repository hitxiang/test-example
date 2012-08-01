package com.playfish.palindrome.action;

import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	
	@Action(value="index")
	public String send()
	{
		return "index";
	}
	
	@Action(value="action-one")
	public String actionOne() {
		
		// determin if the input is palindrome
		// if it is return score
		// else return hint: the Longest Palindromic Substring
		return "action-one";
	}
}
