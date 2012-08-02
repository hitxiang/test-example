package com.playfish.palindrome.action;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.playfish.palindrome.model.User;
import com.playfish.palindrome.util.StringUtil;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	
	@Action(value="index")
	public String send()
	{

	  return "index";
	}
	
	@Action(value="load")
	public String load() {
		
	  String uuid = getUUID();
	  
		// determin if the input is palindrome
		// if it is return score
		// else return hint: the Longest Palindromic Substring
		return "action-one";
	}
	
	
	@Action(value="submit")
	public String submit() {
	                
	          String uuid = getUUID();
	          
	                // determin if the input is palindrome
	                // if it is return score
	                // else return hint: the Longest Palindromic Substring
	                return "action-one";
	        }
	
	       @Action(value="palindrome_register")
	        public String register() {
	                        
	                  String uuid = getUUID();
	                  
	                        // determin if the input is palindrome
	                        // if it is return score
	                        // else return hint: the Longest Palindromic Substring
	                        return "action-one";
	                }
	
        private String getUUID() {
          Map<String, Object> session = ActionContext.getContext().getSession();
          String uuid = (String)session.get(User.IDENTIFY_TAG);
          if (StringUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            session.put(User.IDENTIFY_TAG, uuid);
            session.put(User.IS_NEW_TAG, Boolean.FALSE);
          } 
          return uuid;
        }
        

}
