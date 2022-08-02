package com.yupi.usercenter.model.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 *
 * @author HP
 */
@Slf4j
@Data
public class ExportVO {

    /**
     * 用户id(主键)
     */
    private Long id;

    /**
     * 昵称
     */
    private String username;


    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 性别名字
     */
    private String genderName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
