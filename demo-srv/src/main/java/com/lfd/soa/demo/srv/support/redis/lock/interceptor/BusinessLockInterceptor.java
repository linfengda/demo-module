package com.lfd.soa.demo.srv.support.redis.lock.interceptor;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.demo.srv.support.redis.lock.RedisDistributedLock;
import com.lfd.soa.demo.srv.support.redis.lock.builder.BusinessLockMetaBuilder;
import com.lfd.soa.demo.srv.support.redis.lock.meta.LockKeyMeta;
import com.lfd.soa.demo.srv.support.redis.lock.meta.LockMethodMeta;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * 描述: 业务锁方法拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class BusinessLockInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("拦截业务锁方法{}，参数：{}", invocation.getMethod().getName(), invocation.getArguments());
        // 解析业务锁方法元数据
        LockMethodMeta lockMethodMeta = BusinessLockMetaBuilder.getLockMethodMeta(invocation.getMethod());
        // 获取业务锁key
        String key = parseKey(lockMethodMeta, invocation.getArguments());
        // 加锁解锁
        try {
            if (!RedisDistributedLock.tryLock(key)) {
                throw new BusinessException("获取业务锁[" + key + "]失败！");
            }
            return invocation.proceed();
        }finally {
            RedisDistributedLock.unLock(key);
        }
    }

    /**
     * 初始化业务锁key
     * @param lockMethodMeta    key元数据
     * @param arguments         参数
     * @return                  业务锁key
     */
    private String parseKey(LockMethodMeta lockMethodMeta, Object[] arguments) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append(lockMethodMeta.getPrefix());
        sb.append(":");
        List<LockKeyMeta> lockKeys = lockMethodMeta.getLockKeys();
        for (LockKeyMeta lockKey : lockKeys) {
            Object key = arguments[lockKey.getKeyParameterIndex()];
            if (!StringUtils.isEmpty(lockKey.getKeyField())) {
                Field field = lockKey.getKeyField();
                field.setAccessible(true);
                key = field.get(key);
            }
            sb.append(Optional.ofNullable(key).orElse(""));
        }
        return sb.toString();
    }
}