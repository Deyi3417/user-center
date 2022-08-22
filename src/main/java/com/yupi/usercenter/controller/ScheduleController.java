package com.yupi.usercenter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HP
 * @date 2022-08-05
 */
@Component
@Slf4j
public class ScheduleController {

    @Async
    @Scheduled(cron = "0/2 * * * * ?")
    public void testSchedule() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        log.info("schedule is start");
        System.out.println("--*--*--: " + format + " 开心 快乐每一天！");
        log.info("schedule is ending");
    }

}
