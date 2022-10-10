package com.yupi.usercenter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power.common.util.RandomUtil;
import com.yupi.usercenter.common.redis.RedisIdWorker;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import com.yupi.usercenter.service.TicketService;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class UsercenterApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Autowired
    private TicketService ticketService;

    @Test
    void contextLoads() {
        String randomNumbers = RandomUtil.randomNumbers(6);
        log.info("\n====randomNumbers==== " + randomNumbers);
    }

    @Test
    void stringRedisTemplates() {
        stringRedisTemplate.opsForValue().set("gender", "1");
        log.info("保存成功");
    }

    @Test
    void stringRedisTemplates02() {
        User user = userService.getUserById(3);
        // 使用json字符串保存到redis
        stringRedisTemplate.opsForValue().set("user02", JSON.toJSONString(user));
        log.info("====程序结束====");
    }

    @Test
    void getFromRedis() {
        // 从redis获取
        String userString = stringRedisTemplate.opsForValue().get("user");
        System.out.println("user : " + userString);

        // json字符串转为User对象
        User user = JSON.parseObject(userString, User.class);
        System.out.println("user : " + user);
        log.info("====程序结束====");
    }

    /**
     * 保存信息到 redis
     */
    @Test
    void hashRedis() {
        User user = userService.getUserById(1);
        UserDTO userDTO = new UserDTO();
        // user 拷贝到 userDTO 字段对应
        BeanUtils.copyProperties(user, userDTO);
        log.info("\n====userDTO====\n" + userDTO);
        Map map = JSON.parseObject(JSON.toJSONString(userDTO), Map.class);
        log.info("\n====程序结束====\n" + map);

        // 设置用户登录的 token
        String token = UUID.randomUUID().toString();
        log.info("\n====token: " + token);
        // token 作为主键-将userDTO信息存入 redis
        redisTemplate.opsForHash().putAll(token, map);
        // 设置 token 的过期时间
        redisTemplate.expire(token, 4L, TimeUnit.MINUTES);
    }

    /**
     * 从 redis 获取 hash
     */
    @Test
    void getHashFromRedis() {
        Map entries = redisTemplate.opsForHash().entries("8423180e-3669-4701-b2fb-b26b0559123f");
        log.info("entries:\n " + entries);
        // 将 map entries 转为 UserDTO  map--转为json字符串
        UserDTO userDTO = JSON.parseObject(JSON.toJSONString(entries), UserDTO.class);
        log.info("\n====userDTO====" + userDTO);
    }


    @Test
    void deleteRedisKey() {
        Boolean flag = stringRedisTemplate.delete("user02");
        log.info("====程序运行成功====删除redis: " + flag+"====:" + BooleanUtils.isTrue(flag));
    }

    @Test
    void updateSQL() {
        /*
        boolean update = ticketService.update().setSql("ticket_level = 9, handle_way = 9").eq("id", 2).update();
        if (update) {
            System.out.println("update setSql==== done ====");
        }

         */
        QueryWrapper<Ticket> query = new QueryWrapper<>();
        query.eq("ticket_code", "220826666").eq("user_id", 3);
        // Ticket ticket = ticketService.getOne(query);
        long count = ticketService.count(query);
        System.out.println("====Done==== " + count);

        /*
             非事务的方法调用自身类的事务方法，事务失效
             获得代理对象
         */
        Object o = AopContext.currentProxy();
        System.out.println("==O== " + o);
        TicketService ticketService02 = (TicketService) o;
        List<Ticket> list = ticketService02.list();
        System.out.println("====list====" + list);


    }

    @Test
    void testTime() {
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - 1640995200L;
        int COUNT_BITS = 32;

        // 生成序列号
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 自增长
        Long increment = stringRedisTemplate.opsForValue().increment("icr:" + "dy23" + ":" + date);
        // 3.拼接并返回
        System.out.println("result:" + (timestamp << COUNT_BITS | increment));
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Test
    void testIdWorker() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                long redisId = redisIdWorker.nextLongId("liudy23");
                log.info("redis = {}", redisId);
            }
            countDownLatch.countDown();
        };
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            executorService.submit(task);
            System.out.println("liudy23" + task);
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("time = " + (endTime - beginTime));
    }
}
