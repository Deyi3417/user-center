package com.yupi.usercenter;

import com.alibaba.fastjson.JSON;
import com.power.common.util.RandomUtil;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.UUID;
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
        stringRedisTemplate.opsForValue().set("user", JSON.toJSONString(user));
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

    @Test
    void hashRedis() {
        User user = userService.getUserById(3);
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
        redisTemplate.expire(token, 60L, TimeUnit.SECONDS);
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


}
