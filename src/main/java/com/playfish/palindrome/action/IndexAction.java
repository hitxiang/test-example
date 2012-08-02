package com.playfish.palindrome.action;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.playfish.palindrome.model.User;
import com.playfish.palindrome.service.PalindromeGame;
import com.playfish.palindrome.util.StringUtil;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {

	@Action(value = "index")
	public String send() {

		return "index";
	}

	@Action(value = "load")
	public String load() {

		User u = getUser();

		return "action-one";
	}

	@Action(value = "submit")
	public String submit() {

		User u = getUser();
		//TODO
		int score = 0;
		PalindromeGame.update(u, score);

		// determin if the input is palindrome
		// if it is return score
		// else return hint: the Longest Palindromic Substring
		return "action-one";
	}

	@Action(value = "palindrome_register")
	public String register() {
		// TODO
        String name = null;
        User u = getUser();

		u = PalindromeGame.registerUser(u.getId(), name);
		updateUser(u);
		return "action-one";
	}

	private User getUser() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User u = (User) session.get(User.IDENTIFY_TAG);
		if (u == null) {
			u = PalindromeGame.addUser(UUID.randomUUID().toString());
			session.put(User.IDENTIFY_TAG, u);			
		}
		return u;
	}
	
	private void updateUser(User u) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(User.IDENTIFY_TAG, u);;
	}
	
	

}
