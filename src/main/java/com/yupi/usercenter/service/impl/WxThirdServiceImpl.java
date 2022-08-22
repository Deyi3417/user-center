package com.yupi.usercenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.constant.WxConstant;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.IWxThirdService;
import com.yupi.usercenter.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信模板消息服务实现类
 *
 * @author HP
 * @date 2022/08/22
 */
@Service
@Slf4j
public class WxThirdServiceImpl extends ServiceImpl<UserMapper, User>
        implements IWxThirdService {

    @Resource
    private UserMapper userMapper;


    @Override
    public String sendTemplateMessage(String data) {
        // 获取Access_token
        String wxAccessToken = "";
        // TODO: 2022/8/22  redis 缓存-
        String appSecret = WxConstant.wxAppsecret;
        String appid = WxConstant.wxAppID;
        wxAccessToken = getWxAccessToken(appid, appSecret);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + wxAccessToken;
        String result = HttpUtils.httpPost(requestUrl, data);
        return result;
    }

    private String getWxAccessToken(String appid, String appSecret) {
        String accessTokenUrl = WxConstant.wxAccessTokenUrl;
        String url = String.format("%s?grant_type=client_credential&appid=%s&secret=%s", accessTokenUrl, appid, appSecret);
        String result = HttpUtils.get(url);
        JSONObject jsonObject = JSON.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        // TODO: 2022/8/22 redis缓存
        return accessToken;
    }
}
