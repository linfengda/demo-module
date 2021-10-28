package com.lfd.soa.demo.srv.support.redis.lock;

import com.lfd.soa.common.util.ServerRunTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 描述: Redis分布式锁
 *
 * @author linfengda
 * @create 2020-03-23 17:33
 */
@Slf4j
public class RedisDistributedLock {
    /**
     * 锁默认超时时间
     */
    private final static int DEFAULT_LOCK_EXPIRE_TIME = 60;
    /**
     * 阻塞锁最大阻塞时间
     */
    private final static long DEFAULT_LOCK_WAIT_TIME = 30 * 1000L;
    /**
     * redisTemplate
     */
    private static RedisTemplate<String, Object> redisTemplate;

    public static void init(RedisTemplate<String, Object> redisTemplate) {
        RedisDistributedLock.redisTemplate = redisTemplate;
    }

    /**
     * 加锁操作：单个
     * @param key
     * @return
     */
    public static Boolean tryLock(String key) {
        String currentLockRequester = getCurrentLockRequester();
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, currentLockRequester);
        if (result) {
            redisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            log.info("key={}，currentLockRequester={}，lock success", key, currentLockRequester);
        }
        return result;
    }

    /**
     * 阻塞锁：单个
     * @param key
     * @return
     */
    public static Boolean lock(String key) {
        long startTime = System.currentTimeMillis();
        while (!tryLock(key)) {
            try {
                if (DEFAULT_LOCK_WAIT_TIME < System.currentTimeMillis() - startTime) {
                    return false;
                }
                Thread.sleep(30);
            } catch (InterruptedException e) {
                log.error("获取redis阻塞锁失败！", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 解锁操作：单个
     * @param key
     * @return
     */
    public static Boolean unLock(String key) {
        Object lockRequester = redisTemplate.opsForValue().get(key);
        String currentLockRequester = getCurrentLockRequester();
        if (null == lockRequester) {
            return true;
        }
        if (lockRequester.equals(currentLockRequester)) {
            redisTemplate.delete(key);
            log.info("key={}，currentLockRequester={}，unlock success", key, currentLockRequester);
            return true;
        }
        return false;
    }

    /**
     * 获取当前加锁者
     * @return jvmName+ip+threadName+threadID
     */
    private static String getCurrentLockRequester() {
        StringBuilder builder = new StringBuilder();
        builder.append(ServerRunTimeUtil.getJvmName());
        builder.append(ServerRunTimeUtil.getIp());
        builder.append(Thread.currentThread().getName());
        builder.append(Thread.currentThread().getId());
        return builder.toString();
    }
}
