package com.stanlong;

import com.stanlong.bean.User;
import com.stanlong.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testUserMapper(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
}
