package com.yupi.usercenter.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.model.domain.vo.UserVo;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        User user1 = new User();
        UserVo user = userService.obtainUser(id);
        return user;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("liudy23测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 带写入的数据
        List<User> userList = userService.getUserList();
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        log.info("liudy23执行了");
        List<ExportVO> userVOList = userService.getExportUser();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String format = sdf.format(new Date());
        String fileName = URLEncoder.encode("用户表-"+format, "UTF-8").replaceAll("\\+", "%20");
//        String fileName = new String("问题清单".getBytes("gbk"), "iso8859-1");
//        response.setHeader("Content-disposition", "attachment; filename=");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + ".xls\"");
        ServletOutputStream os = null;
        os = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(os).withTemplate("D:/File_liudy23/temp/template/userTemplate.xlsx").build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
//        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).build();
        os.flush();
        excelWriter.fill(userVOList, writeSheet);
        excelWriter.finish();
        os.close();
    }

}
