package com.yupi.usercenter.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.constant.DateUtil;
import com.yupi.usercenter.enums.GenderEnum;
import com.yupi.usercenter.listener.EasyExcelListener;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.ImportUserData;
import com.yupi.usercenter.model.domain.vo.DownloadDataVO;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Consumer;
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
@Slf4j
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

    @GetMapping("/import")
    @ApiOperation("测试导入")
    public void testImport() {
        EasyExcel.read("D:/tmp/easyExcel/user.xls", ImportUserData.class, new EasyExcelListener())
                .sheet()
                .doRead();
    }

    @PostMapping("/import02")
    @ApiOperation("测试导入")
    public void testImport(MultipartFile uploadExcelFile) {
        try {
            InputStream inputStream = uploadExcelFile.getInputStream();
            List<ImportUserData> importUserDataList = new ArrayList<>();
            EasyExcel.read(inputStream, ImportUserData.class, new PageReadListener<ImportUserData>(importUserDataList::addAll)).sheet().doRead();
            EasyExcel.read(inputStream, ImportUserData.class, new PageReadListener(new Consumer<List>() {
                        @Override
                        public void accept(List list) {
                            importUserDataList.addAll((Collection<? extends ImportUserData>) list);
                        }
                    }))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/testEnum")
    @ApiOperation("测试枚举")
    public void testEnum(@RequestParam String name) {
        for (GenderEnum enumValue : GenderEnum.values()) {
            System.out.println(enumValue.getCode() + "----" + enumValue.getName());
        }
        Integer codeByName = GenderEnum.getCodeByName(name);
        System.out.println("result:" + codeByName);
    }

    @GetMapping("expToLocal")
    @ApiOperation("使用easyExcel导出数据到本地文件夹")
    public BaseResponse<?> expToLocal() {
        String templateFileName = "D:\\File_liudy23\\temp\\template\\userTemplate.xlsx";
        String dateString = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        StringBuffer fileName = new StringBuffer();
        fileName.append("liudy23_").append(dateString).append(".xlsx");
        String outputFileName = "D:\\tmp\\easyExcel\\" + fileName;
        ExcelWriter writer = EasyExcel.write(outputFileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        List<ExportVO> exportUser = userService.getExportUser();
        writer.fill(exportUser, writeSheet);
        writer.finish();
        return ResultUtils.success(outputFileName);
    }

    @GetMapping("downLocalFile")
    @ApiOperation("根据路径下载文件")
    public BaseResponse<?> downLocalFileByPath(String filePath, HttpServletResponse response) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        response.setContentType("application/force-download");
        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ServletOutputStream os = response.getOutputStream();
            IOUtils.copyLarge(fileInputStream,os);
            os.flush();
            os.close();
            fileInputStream.close();
            return ResultUtils.success(filePath);
        } catch (Exception e) {
            log.error("操作异常,无法下载");
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
    }


    @GetMapping("downLocalFile02")
    @ApiOperation("根据路径下载文件02")
    public BaseResponse<?> downLocalFileByPath02(String filePath, HttpServletResponse response) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        response.setContentType("application/force-download");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ServletOutputStream os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int n;
            while ((n=fileInputStream.read(bytes)) != -1) {
                os.write(bytes, 0, n);
                os.flush();
            }
            os.flush();
            os.close();
            fileInputStream.close();
            return ResultUtils.success(filePath);
        } catch (Exception e) {
            log.error("操作异常,无法下载");
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
    }
}
