package com.yupi.usercenter.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : liudy23
 * @date : 2022/11/9
 */
@Component
public class XxlJobHandler {

    private static Logger logger = LoggerFactory.getLogger(XxlJobHandler.class);

    private String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 简单的任务示例
     */
    @XxlJob("testHandlerDemo")
    public ReturnT<String> xxlJobHandler(String name) {
        XxlJobLogger.log("XXL-JOB, Learning!");
        System.out.println("====" + DateFormatUtils.format(new Date(),PATTERN) + " 阳光明媚，风和日丽！");
        // todo 通知逻辑，以什么形势通知目标人
        return ReturnT.SUCCESS;
    }

}
