package com.playfish.palindrome.action;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.playfish.palindrome.model.Score;
import com.playfish.palindrome.model.User;
import com.playfish.palindrome.service.PalindromeGame;
import com.playfish.palindrome.util.StringUtil;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
  private final static Logger logger = Logger.getLogger(IndexAction.class);

  private String palindromeString;
  private String name;
  private User user;

  @Action(value = "index")
  public String send() {
    return "index";
  }

  @Action(value = "load")
  public String load() {
    loadUser();
    return "load";
  }

  @Action(value = "submit")
  public String submit() {

    User u = loadUser();

    if (!StringUtils.isEmpty(palindromeString)) {
      if (u.isSameWithLast(palindromeString)) {
        addActionMessage("The input is the same with last time.");
      } else {
        u.setLastInput(palindromeString);
        int score = StringUtil.countPalindrome(palindromeString);
        if (score > 0) {
          PalindromeGame.updateRanks(u, score);
        }
        addActionMessage(getMessages(score));
      }
    } else {
      addActionMessage("The input is empty.");
    }

    return "load";
  }

  @Action(value = "name.action")
  public String name() {
    return "name";
  }

  @Action(value = "register")
  public String register() {
    User u = loadUser();
    u = PalindromeGame.registerUser(u.getId(), this.name);
    return "load";
  }

  @Action(value = "ranks")
  public String ranks() {
    return "ranks";
  }

  public String getPalindromeString() {
    return palindromeString;
  }

  public void setPalindromeString(String palindromeString) {
    this.palindromeString = palindromeString;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getUser() {
    return this.user;
  }

  public Score[] getHighestRanks() {
    return PalindromeGame.getHighestScoreRanks();
  }

  public Score[] getTotalRanks() {
    return PalindromeGame.getTotalScoreRanks();
  }

  public String getMessages(long score) {
    String messages;
    if (score == 0) {
      messages = "Pity, It is not a palindrome! Have another try! ";
    } else if (score < 3) {
      messages = "Not bad, try more! ";
    } else if (score < 6) {
      messages = "Good job, well done! ";
    } else if (score < 11) {
      messages = "You are great, please check the highest score rank!";
    } else {
      messages = "You are amazing! You have a chance to be the top!";
    }
    return messages;
  }


  // Should use Filter/Intercepter like function of struts
  private User loadUser() {
    Map<String, Object> session = ActionContext.getContext().getSession();
    String uuid = (String) session.get(User.IDENTIFY_TAG);
    if (uuid == null) {
      uuid = UUID.randomUUID().toString();
      session.put(User.IDENTIFY_TAG, uuid);
      this.user = PalindromeGame.addUser(uuid);
      return this.user;
    }
    this.user = PalindromeGame.getUser(uuid);
    return this.user;
  }


}
