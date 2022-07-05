package com.stanlong.service;

import com.stanlong.bean.User;
import com.stanlong.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUser(Long id){
        return userMapper.getUser(id);
    }
}
