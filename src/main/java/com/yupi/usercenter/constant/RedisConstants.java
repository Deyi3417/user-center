package com.yupi.usercenter.constant;

import org.ehcache.Cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redis 常量
 *
 * @author : HP
 * @date : 2022/9/2
 */
public class RedisConstants {

    public static final String REDIS_USER_HASH_ID_KEY = "redis:user:id:";
    public static final Long REDIS_USER_TTL = 2L;

    public static final String REDIS_TICKET_HASH_ID_KEY = "redis:ticket:id:";
    public static final Long REDIS_TICKET_TTL = 3L;

    public static final String REDIS_TICKET_LOCK_KEY = "redis:lock:ticket:id:";

    /**
    例1:Static final ConcurrentHashMap<K,V> map = new
            ConcurrentHashMap >(); 本地用于高并发
    例2:static final Cache<K,V> USER_CACHE =
            CacheBuilder.newBuilder().build(); 用于redis等缓存
    例3:Static final Map<K,V> map =  new HashMap(); 本地缓存
     */

}
