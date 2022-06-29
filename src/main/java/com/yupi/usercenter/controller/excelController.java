package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.constant.DateUtil;
import com.yupi.usercenter.constant.UserConstant;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户注册接口
 *
 * @author HP
 * @create 2022/5/1 20:46
 */
@RestController
@RequestMapping("/excelPort")
public class excelController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/search")
    public List<User> searchUser(String username, HttpServletRequest request) {
        // 鉴权，仅管理员可以查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            // 默认模糊查询
            userQueryWrapper.like("username",username);
        }
        List<User> userList = userService.list(userQueryWrapper);
        return userList.stream().map(user1 -> userService.getSafetyUser(user1)).collect(Collectors.toList());
    }
    @GetMapping("/all")
    public List<User> all() {
        return userMapper.selectList(new QueryWrapper<User>().eq("isDelete",0));
    }
    @GetMapping("/myTest")
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> listUsers = userService.getUsers();
        for (Map<String, Object> user : listUsers) {
            user.put("address","衡阳");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("hobby","打篮球");
            map1.put("kill","写代码");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("position","计算机软件开发工程师");
            map2.put("dept","软件所");
            user.put("map1",map1);
            user.put("map2",map2);
        }
        return listUsers;

    }


    @GetMapping("/export")
    public void export() {
        try {

            List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("isDelete", 0));

            List<Map<String, Object>> listUsers = userService.getUsers();
            for (Map<String, Object> user : listUsers) {
                user.put("address","衡阳");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("hobby","打篮球");
                map1.put("kill","写代码");
                Map<String, Object> map2 = new HashMap<>();
                map2.put("position","计算机软件开发工程师");
                map2.put("dept","软件所");
                user.put("map1",map1);
                user.put("map2",map2);
            }

            String tplName = "/templateXLS/userTemplate.xlsx";
            Map<String, Object> mapList = new HashMap<>();
            mapList.put("list",listUsers);

            Workbook wb = userService.getWorkbookByTpl(tplName, mapList);

            String fileName = "用户列表";

            userService.renderExcel(wb, tplName, fileName + "_" + DateUtil.getDays() + ".xls");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
