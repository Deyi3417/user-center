package com.yupi.usercenter.common.redis;

import com.power.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 *
 * @author : HP
 * @date : 2022/9/6
 */
@Slf4j
public class SimpleRedisLock implements ILock {

    private String name;

    private StringRedisTemplate stringRedisTemplate;

    public static final String KEY_PREFIX = "lock:";

    /**
     * 解决Redis分布式锁误删问题
     */
    public static final String ID_PREFIX = UUIDUtil.getUuid() + "-";

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeoutSec) {
        // 获取线程标示
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 获取锁
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
        log.info("====success===" + KEY_PREFIX + name + "====threadId====" + threadId + "");
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlock() {
        // 获取线程标示
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        // 获取锁中的标示
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        log.info("====判断释放锁的条件：{}", id.equals(threadId));
        if (threadId.equals(id)) {
            // 通过del删除锁-只能删除自己的锁
            stringRedisTemplate.delete(KEY_PREFIX + name);
            log.info("====执行了释放锁操作====" + KEY_PREFIX + name);
        }
    }
}
