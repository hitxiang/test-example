package com.playfish.palindrome.dao.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.playfish.palindrome.dao.UserDao;
import com.playfish.palindrome.model.User;

public final class UserDaoLocalImpl implements UserDao {
  private static UserDao instance;

  private final ConcurrentHashMap<String, User> userMap;

  public static synchronized UserDao getInstance() {
    if (instance == null) {
      instance = new UserDaoLocalImpl();
    }
    return instance;
  }


  private UserDaoLocalImpl() {
    userMap = new ConcurrentHashMap<String, User>();
  }

  public User add(String uuid) {
    User u = new User(uuid);
    userMap.put(uuid, u);
    return u;
  }

  public User get(String uuid) {
    return userMap.get(uuid);
  }

  public User register(String uuid, String name) {
    User u = userMap.get(uuid);
    u.setName(name);
    u.setRegisted(true);

    return u;
  }

  public void clear() {
    userMap.clear();
  }
}
