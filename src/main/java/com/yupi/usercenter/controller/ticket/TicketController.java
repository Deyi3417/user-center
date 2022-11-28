package com.yupi.usercenter.controller.ticket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.PageSearchDTO;
import com.yupi.usercenter.service.TicketService;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : HP
 * @date : 2022/8/26
 */
@RestController
@RequestMapping("/ticket")
@Slf4j
@Api(tags = "不合格票控制器")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public BaseResponse<Ticket> ticketList() {
        log.info("====获取车票列表====");
        Ticket ticket = ticketService.getById(1);
//        List<Ticket> ticketList = ticketService.list();
        return ResultUtils.success(ticket);
    }

    @GetMapping("/ticketList")
    @ApiOperation("获取不合格票列表")
    public BaseResponse getTicketList(@RequestParam PageSearchDTO pageParams) {
        log.debug("测试分页查询，模糊搜索，以及导出excel表，主要还是测试@RequestBody与Post请求方式搭配");
        Long current = pageParams.getCurrent();
        if (ToolUtil.isEmpty(current)) {
            current = 0L;
        }
        Long size = pageParams.getSize();
        if (ToolUtil.isEmpty(size)) {
            size = 99999L;
        }

        Page<Ticket> page = new Page<>(current, size);
        List<Ticket> ticketList = ticketService.getTicketList(page, pageParams);
        Page<Ticket> ticketPage = page.setRecords(ticketList);
        return ResultUtils.success(ticketPage);
    }


    @GetMapping("/list02")
    public BaseResponse<List<User>> userList() {
        log.info("====获取用户列表====");
        List<User> userList = userService.list();
        return ResultUtils.success(userList);
    }

    @PostMapping("/getTicket")
    public BaseResponse<Ticket> getTicketById(){
        return null;
    }

    @PostMapping("/insert")
    public BaseResponse<?> insertTicket() {
        ticketService.insertTicket();
        return ResultUtils.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("分页查询")
    public BaseResponse<?> list(long current, long size) {
        Page<Ticket> ticketPage = new Page<>(current, size);
        List<Ticket> ticketList = ticketService.selectList(ticketPage,null);
        Page<Ticket> ticketPage1 = ticketPage.setRecords(ticketList);
        return ResultUtils.success(ticketPage1);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询02")
    public BaseResponse<?> page(long current, long size) {
        Page<Ticket> ticketPage = new Page<>(current, size);
        List<Ticket> ticketList = ticketService.selectList(ticketPage,null);
        Page<Ticket> ticketPage1 = ticketPage.setRecords(ticketList);
        return ResultUtils.success(ticketPage1);
    }

}
