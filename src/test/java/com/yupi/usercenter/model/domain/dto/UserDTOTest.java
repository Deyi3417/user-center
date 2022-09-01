package com.yupi.usercenter.model.domain.dto;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@SpringBootTest
@Slf4j
class UserDTOTest {

    @Autowired
    private UserService userService;

    @Test
    void testCopyDTO() {
        User user = userService.getUserById(3);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        System.out.println("usr: " + user);
        System.out.println("userDTO: " + userDTO);
        log.info("====程序错误====");

    }

}