package com.yupi.usercenter.model.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HP
 * @date 2022/08/12
 */
@Data
public class TestVO implements Serializable {

    private static final long serialVersionUID = -8974035349684656674L;

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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 格式化时间
     */
    private Date updateTime;

    /**
     * 更新时间
     */
    private Date formatTime;
}
