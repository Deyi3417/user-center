package com.yupi.usercenter.controller;

import com.yupi.usercenter.model.domain.vo.UserVo;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * liudy23学习接口
 * @author HP
 * @date 2022-07-01
 */
@Slf4j
@RestController
@RequestMapping("/learn")
public class LearnController {

    @Resource
    private UserService userService;

    @GetMapping("/user")
    public UserVo getUserById(@RequestParam Integer id) {
        UserVo user = userService.obtainUser(id);
        return user;
    }

}
