package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.mapstruct.basic.UserConvert2DTO;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.SafetyUserDTO;
import com.yupi.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : HP
 * @date : 2022/12/6
 */

@RestController
@RequestMapping("/test")
@Api(tags = "测试控制器")
public class TestController {

    @Resource
    private UserService userService;

    @ApiOperation("获取脱敏后的用户信息")
    @GetMapping("/getSafetyUser")
    public BaseResponse<SafetyUserDTO> getSafeUser(String id) {
        User user = userService.getById(id);
        SafetyUserDTO resu = UserConvert2DTO.INSTANCE.toCovetSafetyUserDTO(user);
        return ResultUtils.success(resu);
    }

    @ApiOperation("获取脱敏后的用户信息List")
    @PostMapping("/listSafetyUser")
    public BaseResponse<List<SafetyUserDTO>> getListSafetyUser(@RequestBody List<Integer> ids) {
        List<User> users = userService.list(new QueryWrapper<User>().in("id", ids).eq("is_delete", 0));
        List<SafetyUserDTO> result = UserConvert2DTO.INSTANCE.toCovetSafetyUserDTOList(users);
        return ResultUtils.success(result);
    }
}
