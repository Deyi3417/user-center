package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.SafetyUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
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

    @Test
    public void testSearchUsersByTags() {
        List<String> tagsNameList = Arrays.asList("Java","Python");
        List<SafetyUserDTO> resultUsers = userService.searchUsersByTags(tagsNameList);
        Assertions.assertNotNull(resultUsers);
    }

    @Test
    public void testHttpStatus() {
        System.out.println(HttpStatus.OK.value());
    }

}