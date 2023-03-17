package com.yupi.usercenter.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.mapstruct.basic.TicketConvert2DTO;
import com.yupi.usercenter.mapstruct.basic.UserConvert2DTO;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import com.yupi.usercenter.model.domain.dto.UserDTO2;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.model.domain.vo.TestVO;
import com.yupi.usercenter.model.domain.vo.UserPullVO;
import com.yupi.usercenter.model.domain.vo.UserVo;
import com.yupi.usercenter.service.TicketService;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * liudy23学习接口
 * @author HP
 * @date 2022-07-01
 */
@Slf4j
@RestController
@Api(tags = "Learning Controller")
@RequestMapping("/learn")
public class LearnController {

    @Resource
    private UserService userService;

    @Resource
    private TicketService ticketService;

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
    @ApiOperation("导出excel表格-有模板")
    public void exportExcel(HttpServletResponse response) throws IOException {
        log.info("liudy23执行了");
        List<ExportVO> userVOList = userService.getExportUser();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String format = sdf.format(new Date());
        String fileName = URLEncoder.encode("用户表-"+format, "UTF-8").replaceAll("\\+", "%20");
//        String fileName = new String("问题清单".getBytes("gbk"), "iso8859-1");
//        response.setHeader("Content-disposition", "attachment; filename=");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + ".xls\"");
        ServletOutputStream os = null;
        os = response.getOutputStream();
//        ExcelWriter excelWriter = EasyExcel.write(os).withTemplate("D:/File_liudy23/temp/template/userTemplate.xlsx").build();
        // 这样可以读到相对路径的文件
        InputStream stream = ResourceUtil.getStream("static/template/excel/userTemplate.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(os).withTemplate(stream).build();

        // 方式2
        // EasyExcel.write(os).withTemplate(ResourceUtil.getStream("模板文件相对路径：static/userTemplate.xlsx")).excelType(ExcelTypeEnum.XLSX).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
//        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).build();
        os.flush();
        excelWriter.fill(userVOList, writeSheet);
        excelWriter.finish();
//        excelWriter.close();
        os.close();
    }

    @GetMapping("/downloadExcel")
    @ApiOperation("下载excel表格-非模板")
    public void downloadExcel(HttpServletResponse response) {
        try {
            List<ExportVO> userVOList = userService.getExportUser();
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
            String format = sdf.format(new Date());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("用户表-" + format, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExportVO.class).sheet("模板").doWrite(userVOList);
        } catch (IOException e) {
            log.error("导出excel表格失败：",e);
        }
    }

    /*
     * 导出excel easyExcel版本的问题

    @GetMapping("/exportExcel")
    @ApiOperation("工艺纪律检查导出excel")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        try {
            Page<CraftDisciplineCheckListVO> page = new PageFactory<CraftDisciplineCheckListVO>().defaultNoPage();
            List<CraftDisciplineCheckListVO> exportData = craftDisciplineCheckService.getWebCraftListWithParams(page, params);

//        String fileName = String.format("工艺纪律检查表_%s", DateUtil.format(new Date(), "yyyyMMdd"));
            response.reset();
            response.setContentType("multipart/form-data;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls\"");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            String format = DateFormatUtils.format(new Date(), "yyMMddHHmmss");
            String fileName = URLEncoder.encode("工艺纪律检查表-" + format, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            ServletOutputStream os = null;
            os = response.getOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(os).withTemplate("D:/tmp/doc/exportTemplate/craftTemplate.xls").excelType(ExcelTypeEnum.XLS).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            os.flush();
            excelWriter.fill(exportData, writeSheet);
            excelWriter.finish();
            os.close();
            log.info("导出工艺纪律检查票数据成功");
        } catch (IOException e) {
            log.error("导出工艺纪律检查数据失败：", e);
        }
    }
     */

    @PostMapping("/testJSON")
    public List<TestVO> getUserVo() {
        List<TestVO> userVos = userService.getUserVO();
        return userVos;
    }

    @ApiOperation("获取用户测试: id=3")
    @GetMapping("/getUser")
    public BaseResponse<UserDTO> getUser() {
        User user = userService.getUserById(3);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return ResultUtils.success(userDTO);
    }

    @ApiOperation("根据用户id获取用户测试--BeanUtils浅拷贝DTO")
    @GetMapping("/copy/{id}")
    public BaseResponse<UserDTO> copyProperties(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        UserDTO userDTO = new UserDTO();
        // 浅拷贝
        BeanUtils.copyProperties(user, userDTO);
        return ResultUtils.success(userDTO);
    }

    @ApiOperation("--mapstruct深拷贝userDTO")
    @GetMapping("/user/mapstruct/{id}")
    public BaseResponse<UserDTO> mapstructUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        // 深拷贝
        UserDTO dto = UserConvert2DTO.INSTANCE.toCovertUserDTO(user);
        return ResultUtils.success(dto);
    }

    @ApiOperation("--mapstruct深拷贝ticketDTO")
    @GetMapping("/ticket/mapstruct/{id}")
    public BaseResponse<TicketDTO> mapstructTicket(@PathVariable("id") Long id) {
        Ticket ticket = ticketService.getById(id);
        // 深拷贝
        TicketDTO ticketDTO = TicketConvert2DTO.INSTANCE.toConvertTicketDTO(ticket);
        return ResultUtils.success(ticketDTO);
    }

    @ApiOperation("--mapstruct深拷贝：dto 拷贝为 POJO")
    @PostMapping("/toUser")
    public BaseResponse<User> mapstructToUser(@RequestBody UserDTO dto) {
        // 深拷贝
        User user = UserConvert2DTO.INSTANCE.toConvertUser(dto);
        return ResultUtils.success(user);
    }


    @ApiOperation("测试DTO在父类的基础上扩展")
    @PostMapping("/testDTO")
    public BaseResponse<User> extendPOJO(@RequestBody UserDTO2 dto) {
        // 深拷贝
        User user = JSONObject.parseObject(JSON.toJSONString(dto), User.class);
        return ResultUtils.success(user);
    }
    @ApiOperation("将List<POJO>转化为List<DTO>")
    @PostMapping("/testCopy")
    public BaseResponse<List<UserDTO>> testCopy() {
        List<User> list = userService.list();
        BeanUtils.copyProperties(list,UserDTO.class);
        return ResultUtils.success();
    }

    @ApiOperation("测试DTO在父类的基础上扩展")
    @PostMapping("/testDTO02/{id}")
    public BaseResponse<UserDTO> extendDTO2(@PathVariable(value = "id") Long id) {
        User user = userService.getById(id);
        UserDTO userDTO = UserConvert2DTO.INSTANCE.toCovertUserDTO(user);
        return ResultUtils.success(userDTO);
    }

    @ApiOperation("测试-array")
    @GetMapping("/str")
    public BaseResponse arrayStr(String str) {
        String[] split = str.split(",");
        List<Ticket> result = ticketService.getTicketByIds(split);
        return ResultUtils.success(result);
    }

    @ApiOperation("测试-传数组")
    @GetMapping("/ids")
    public BaseResponse listInteger(@RequestParam List<Long> ids) {
        List<Ticket> result = ticketService.list(new QueryWrapper<Ticket>().in("id", ids));
        List<Long> userIds = new ArrayList<>();
        List<User> user = userService.list(new QueryWrapper<User>().eq("is_delete", 0).orderBy(true,false,"id"));
        for (User u: user) {
            userIds.add(u.getId());
        }
        for (Long uid : ids) {
            if (userIds.contains(uid)) {
                userIds.remove(uid);
            }
        }

        return ResultUtils.success(userIds);
    }



    @ApiOperation("测试-list")
    @GetMapping("/list")
    public BaseResponse listStr(String str) {
        String[] split = str.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        List<Ticket> result = ticketService.getTicketByIdsList(list);
        return ResultUtils.success(result);
    }

    @ApiOperation("测试-listAdd")
    @GetMapping("/listAdd")
    public BaseResponse getUserList() {
        List<UserPullVO> userList  = new ArrayList<>();
        UserPullVO userPullVO = new UserPullVO();
        userPullVO.setId(1L);
        userPullVO.setUsername("liudy23");
        userList.add(userPullVO);

        userPullVO.setId(2L);
        userPullVO.setUsername("suyao");
        userList.add(userPullVO);

        for (int i = 0; i < 3; i ++) {
            UserPullVO user = new UserPullVO();
            user.setId(10L+i);
            user.setUsername("liudy_" + i);
            userList.add(user);
        }

        return ResultUtils.success(userList);
    }




}
