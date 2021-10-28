package com.lfd.soa.demo.srv.cache;

import com.lfd.soa.common.exception.BusinessException;
import com.lfd.soa.demo.srv.cache.info.UserCacheInfo;
import com.lfd.soa.demo.srv.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 用户token-userInfo缓存
 * @author linfengda
 * @date 2020-09-21 16:56
 */
@Slf4j
@Component
public class UserInfoCache {
    private static final long userTokenExpired = 24*60*60*1000L;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;
    @Resource
    private UserTokenCache userTokenCache;


    public void initCache() {
        log.warn("初始化用户信息缓存...");
    }

    public void clearCache() {
        genericRedisTemplate.delete(SystemCachePrefix.USER_INFO_CACHE);
        log.warn("清除用户信息缓存...");
    }

    public void put(String token, UserCacheInfo userCacheInfo){
        genericRedisTemplate.hashPut(SystemCachePrefix.USER_INFO_CACHE, token, userCacheInfo);
    }

    public void remove(String token){
        genericRedisTemplate.hashDel(SystemCachePrefix.USER_INFO_CACHE, token);
    }

    public UserCacheInfo getUserInfo(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        UserCacheInfo userCacheInfo = genericRedisTemplate.hashGet(SystemCachePrefix.USER_INFO_CACHE, token);
        return userCacheInfo;
    }

    /**
     * 检查当前token有效性
     * @param token
     * @return
     */
    public UserCacheInfo checkUserInfo(String token){
        UserCacheInfo userCacheInfo = this.getUserInfo(token);
        if(null == userCacheInfo || isTokenExpired(userCacheInfo, token)){
            throw new BusinessException("用户token已失效!");
        }
        String currentLoginToken = userTokenCache.getToken(userCacheInfo.getUserId().toString());
        if(!token.equals(currentLoginToken)){
            //当前用户已经在其他设备登陆，token被挤出
            log.info("token被挤出，线上token：{}，被挤出token：{}", currentLoginToken, token);
            this.remove(token);
            throw new BusinessException("用户token被挤出!");
        }
        return userCacheInfo;
    }

    /**
     * 检查用户token是否已经过期
     * @param userCacheInfo
     * @return {@code true} 已过期  {@code false} 未过期
     */
    private boolean isTokenExpired(UserCacheInfo userCacheInfo, String token){
        Long nowMillis = System.currentTimeMillis();
        //空闲时间
        Long freeTime = nowMillis - userCacheInfo.getCacheLastAccessTime();
        if(freeTime >= userTokenExpired){
            this.remove(token);
            return true;
        }
        //重新更新用户最近访问时间
        userCacheInfo.setCacheLastAccessTime(nowMillis);
        this.put(token, userCacheInfo);
        return false;
    }
}
