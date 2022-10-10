package com.yupi.usercenter.common.redis;

/**
 * 锁的基本接口
 *
 * @author : HP
 * @date : 2022/9/6
 */
public interface ILock {

    /**
     * 尝试获取锁
     *
     * @param timeoutSec 过期时间，超时自动释放锁
     * @return true：获取锁成功 false：获取锁失败
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unlock();
}
