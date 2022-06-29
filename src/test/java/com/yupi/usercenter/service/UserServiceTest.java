package com.yupi.usercenter.service;
import java.util.Date;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("刘德意");
        user.setUserAccount("liudy23");
        user.setAvatarUrl("didaidda");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("18811553417");
        user.setEmail("18811553417@163.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

}