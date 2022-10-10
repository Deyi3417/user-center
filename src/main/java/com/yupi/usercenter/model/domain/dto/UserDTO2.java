package com.yupi.usercenter.model.domain.dto;

import com.yupi.usercenter.model.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author : HP
 * @date : 2022/9/25
 */
@Data
@ApiModel("测试DTO在父类的基础上扩展")
public class UserDTO2 extends User {

    private String goods;

    private String foods;
}
