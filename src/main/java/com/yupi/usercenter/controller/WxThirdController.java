package com.yupi.usercenter.controller;

import com.alibaba.fastjson.JSON;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.model.domain.WxTemplateMessage;
import com.yupi.usercenter.service.IWxThirdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.TreeMap;

/**
 * @author : HP
 * @date : 2022/8/22
 */
@RestController
@RequestMapping("/wxThird")
@Slf4j
public class WxThirdController {

    @Autowired
    private IWxThirdService wxThirdService;

    @PostMapping("/sendWxMessage")
    public BaseResponse<?> sendWxMessage() {
        String templateId = "BwZ2jRkndCW6d5KE1kyXVPeDL_-St5UrN7ZaS2pYe0E";
        String touser = "oG9_s6luZT0xr1vY5Ba4ocFgZPm4";
        String url = "https://www.baidu.com";
        WxTemplateMessage message = new WxTemplateMessage();
        message.setTouser(touser);
        message.setTemplate_id(templateId);
        message.setUrl(url);
        message.setTopcolor("#FF0000");
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<>();
        params.put("first", WxTemplateMessage.item("紧急任务开发", "#173177"));
        params.put("keyword1", WxTemplateMessage.item("liudy23", "#173177"));
        params.put("keyword2", WxTemplateMessage.item(time, "#173177"));
        params.put("keyword3", WxTemplateMessage.item("研发项目管理-物料图纸-微信模板消息", "#173177"));
        params.put("remark", WxTemplateMessage.item("2022-08-31是最后截止时间，请注意", "#173177"));
        message.setData(params);
        String data = JSON.toJSONString(message);
        String result = wxThirdService.sendTemplateMessage(data);
        return ResultUtils.success(result);
    }

}
