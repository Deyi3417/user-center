package com.yupi.usercenter.controller;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.constant.RedisConstants;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.mapstruct.basic.UserConvert2DTO;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UpdateTicketDTO;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import com.yupi.usercenter.service.TicketService;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@RestController
@Api(tags = "RedisController 控制器")
public class RedisController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/user/{id}")
    @ApiOperation("Redis 根据ID获取用户信息(脱敏)")
    public BaseResponse<UserDTO> getUser(@PathVariable(value = "id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        UserDTO userDTO = UserConvert2DTO.INSTANCE.toCovertUserDTO(user);
        return ResultUtils.success(userDTO);
    }

    @GetMapping("/ticket/{id}")
    @ApiOperation("Redis 根据ID获取票信息(脱敏)")
    public BaseResponse<TicketDTO> getTicket(@PathVariable(value = "id") Long id) {
        TicketDTO dto = ticketService.getTicketById(id);
        return ResultUtils.success(dto);
    }

    @PostMapping("/ticket/update")
    @ApiOperation("Redis 更新 ticket 数据")
    public BaseResponse updateTicket(@RequestBody UpdateTicketDTO updateTicketDTO) {
        // 先操作数据库
        Long id = updateTicketDTO.getId();
        if (id == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Ticket primary key id cannot be empty");
        }
        return ticketService.updateTicketInfo(updateTicketDTO);
    }

    @GetMapping("/lock/{id}")
    @ApiOperation("利用互斥锁解决 缓存击穿 问题")
    public BaseResponse getTicketLock(@PathVariable(value = "id") Long id) {
        TicketDTO ticketDTO = ticketService.getTicketThroughLock(id);
        return ResultUtils.success(ticketDTO);
    }


}
