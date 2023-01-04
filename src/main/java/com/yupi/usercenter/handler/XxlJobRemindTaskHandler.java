package com.yupi.usercenter.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : HP
 * @date : 2023/1/4
 */
@Component
@Slf4j
public class XxlJobRemindTaskHandler {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @XxlJob("remindTaskDemo")
    public ReturnT<String> remindTaskDemo(String name) {
        log.info("{}==催办任务开始执行", DateFormatUtils.format(new Date(), PATTERN));
        System.out.println("======" + DateFormatUtils.format(new Date(), PATTERN)+ "  liudy23 is so handsome!");
        System.out.println("接收的任务参数--" + name);
        // TODO: 2023/1/4  催办任务代码逻辑
        return ReturnT.SUCCESS;
    }


}
