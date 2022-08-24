package com.yupi.usercenter.constant;

/**
 * 微信消息常量
 *
 * @author : HP
 * @date : 2022/8/22
 */
public interface WxConstant {

    /**
     * wxAppID
     */
    String WX_APPID = "wx1b96083a11590564";

    /**
     * wxAppsecret
     */
    String WX_APPSECRET = "716393f3c83f41ec0e08bd4e33f989f0";

    /**
     * accessTokenUrl
     */
    String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * sendMessageUrl
     */
    String WX_SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
}
