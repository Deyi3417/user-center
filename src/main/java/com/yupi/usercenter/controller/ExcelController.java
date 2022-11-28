package com.yupi.usercenter.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.constant.DateUtil;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.DownloadDataVO;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户注册接口
 *
 * @author HP
 * @date 2022/5/1 20:46
 */
@RestController
@RequestMapping("/excelPort")
@Api("EasyExcel测试类")
public class ExcelController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/search")
    public List<User> searchUser(String username, HttpServletRequest request) {
        // 鉴权，仅管理员可以查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            // 默认模糊查询
            userQueryWrapper.like("username", username);
        }
        List<User> userList = userService.list(userQueryWrapper);
        return userList.stream().map(user1 -> userService.getSafetyUser(user1)).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<User> all() {
        return userMapper.selectList(new QueryWrapper<User>().eq("isDelete", 0));
    }

    @GetMapping("/myTest")
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> listUsers = userService.getUsers();
        for (Map<String, Object> user : listUsers) {
            user.put("address", "衡阳");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("hobby", "打篮球");
            map1.put("kill", "写代码");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("position", "计算机软件开发工程师");
            map2.put("dept", "软件所");
            user.put("map1", map1);
            user.put("map2", map2);
        }
        return listUsers;

    }


    @GetMapping("/export")
    public void export() {
        try {

            List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("isDelete", 0));

            List<Map<String, Object>> listUsers = userService.getUsers();
            for (Map<String, Object> user : listUsers) {
                user.put("address", "衡阳");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("hobby", "打篮球");
                map1.put("kill", "写代码");
                Map<String, Object> map2 = new HashMap<>();
                map2.put("position", "计算机软件开发工程师");
                map2.put("dept", "软件所");
                user.put("map1", map1);
                user.put("map2", map2);
            }

            String tplName = "/templateXLS/userTemplate.xlsx";
            Map<String, Object> mapList = new HashMap<>();
            mapList.put("list", listUsers);

            Workbook wb = userService.getWorkbookByTpl(tplName, mapList);

            String fileName = "用户列表";

            userService.renderExcel(wb, tplName, fileName + "_" + DateUtil.getDays() + ".xls");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download")
    @ApiOperation("导出Excel")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadDataVO.class).sheet("模板").doWrite(data());
    }

    private List<DownloadDataVO> data() {
        List<DownloadDataVO> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DownloadDataVO data = new DownloadDataVO();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }



}
