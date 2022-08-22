package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;

/**
 * 微信模板消息服务类
 *
 * @author : HP
 * @date : 2022/8/22
 */
public interface IWxThirdService extends IService<User> {

    /**
     * 发送微信模板消息
     *
     * @param data 模板消息内容
     * @return 反馈结果
     */
    String sendTemplateMessage(String data);
}
