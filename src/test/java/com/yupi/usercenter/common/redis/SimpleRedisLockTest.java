package com.yupi.usercenter.common.redis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.mapstruct.basic.UserConvert2DTO;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : HP
 * @date : 2022/9/6
 */
@SpringBootTest
@Slf4j
class SimpleRedisLockTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Test
    void testRedisLock() {
        SimpleRedisLock simpleRedisLock = new SimpleRedisLock("redis:ticket:", stringRedisTemplate);
        boolean flag = simpleRedisLock.tryLock(180L);
        if (flag) {
            System.out.println("添加锁成功");
        }
        // 执行
        try {
            Thread.sleep(40000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            simpleRedisLock.unlock();
        }
    }

    /**
     * 测试往redis set 中添加值
     */
    @Test
    void testAddSet() {
        Long add = redisTemplate.opsForSet().add("blog:like:" + 2, 666,777,66,55,77);
//        redisTemplate.expire("blog:like:" + 2,2L, TimeUnit.MINUTES);
        System.out.println("====addSet==== " + add);
    }
    /**
     * 测试 redis set数据类型 是否包含该key value
     * Check if set at key contains value.
     */
    @Test
    void testSetMember() {
        Boolean member = redisTemplate.opsForSet().isMember("blog:like:" + 2, 666);
        System.out.println("====opsForSet====member:" + member);
    }

    /**
     * 移除redis set 中 的 value
     */
    @Test
    void testRemoveSet() {
        Long remove = redisTemplate.opsForSet().remove("blog:like:" + 2, 777);
        System.out.println("====revome==== " + remove);
    }

    /**
     * zSet ADD 有序
     */
    @Test
    void testZSetAdd() {
        redisTemplate.opsForZSet().add("zset:like:" + 6, 1, System.currentTimeMillis());
        redisTemplate.opsForZSet().add("zset:like:" + 6, 2, System.currentTimeMillis());
        redisTemplate.opsForZSet().add("zset:like:" + 6, 3, System.currentTimeMillis());
        redisTemplate.opsForZSet().add("zset:like:" + 6, 4, System.currentTimeMillis());
        redisTemplate.opsForZSet().add("zset:like:" + 6, 5, System.currentTimeMillis());
        redisTemplate.opsForZSet().add("zset:like:" + 6, 6, System.currentTimeMillis());
        System.out.println("====zsetAdd==== ");
    }

    /**
     * ZSet remove
     */
    @Test
    void testZSetRemove() {
        Long remove = redisTemplate.opsForZSet().remove("zset:like:" + 6, 232, 3322);
        System.out.println("====remove==== " + remove);
    }

    @Test
    void testZSetRange() {
        Set<Integer> range = redisTemplate.opsForZSet().range("zset:like:" + 6, 0, 2);
        System.out.println("====range==== " + range);
        for (Integer setStr : range) {
            System.out.println("==> " + setStr);
        }
        List<User> listUsers = userService.list(new QueryWrapper<User>().in("id", range));
        System.out.println("====users====" + listUsers);
        List<UserDTO> userDTOList = new ArrayList<>();
        // 脱敏
        List<UserDTO> userDTOList1 = UserConvert2DTO.INSTANCE.toCovetUserDTOList(listUsers);
        System.out.println("====userDTOList1==== " + userDTOList1);
        for (User user : listUsers) {
            UserDTO userDTO = UserConvert2DTO.INSTANCE.toCovertUserDTO(user);
            userDTOList.add(userDTO);
        }
        System.out.println("====userDTO==== " + userDTOList);
    }

    @Test
    void testZSetScore() {
        Double score = redisTemplate.opsForZSet().score("zset:like:" + 6, 232);
        System.out.println("====score====" + score);
    }




}