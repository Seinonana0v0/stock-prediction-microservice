package edu.hhu.taoran.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.hhu.taoran.mapper.UserMapper;
import edu.hhu.taoran.pojo.User;
import edu.hhu.taoran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User checkLogin(String username, String password) {
        return userMapper.checkLogin(username,password);
    }

    @Override
    public String getToken(User user) {
        Date start = new Date();
        //1小时有效时间
        long endTime = System.currentTimeMillis() + 1000 * 60*60;
        Date end = new Date(endTime);
        String token = JWT.create().withAudience(user.getUsername()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }


}
