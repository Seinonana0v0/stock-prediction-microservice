package edu.hhu.taoran.service;

import edu.hhu.taoran.pojo.User;

public interface UserService {
    User checkLogin(String username,String password);
    String getToken(User user);
    User selectById(Integer id);
    User selectByUsername(String username);

}
