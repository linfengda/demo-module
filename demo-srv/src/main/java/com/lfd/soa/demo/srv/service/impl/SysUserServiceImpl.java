package com.lfd.soa.demo.srv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfd.soa.demo.srv.support.redis.cache.annotation.CacheKey;
import com.lfd.soa.demo.srv.support.redis.cache.annotation.QueryCache;
import com.lfd.soa.demo.srv.support.redis.cache.annotation.UpdateCache;
import com.lfd.soa.demo.srv.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.lfd.soa.demo.srv.support.redis.cache.entity.type.DataType;
import com.lfd.soa.demo.srv.bean.req.UserUpdateReq;
import com.lfd.soa.demo.srv.bean.entity.SysUser;
import com.lfd.soa.demo.srv.bean.resp.UserResp;
import com.lfd.soa.demo.srv.mapper.SysUserMapper;
import com.lfd.soa.demo.srv.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @QueryCache(type = DataType.SET, prefix = "sys:dUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public Set<UserResp> getDepartmentUserList(@CacheKey Integer departmentId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getDepartmentId, departmentId);
        Set<UserResp> userRespSet = new HashSet<>();
        List<SysUser> sysUserList = this.list(lambdaQueryWrapper);
        for (SysUser sysUser : sysUserList) {
            UserResp userResp = getUserVo(sysUser);
            userRespSet.add(userResp);
        }
        return userRespSet;
    }

    @QueryCache(type = DataType.LIST, prefix = "sys:tUser", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true)
    @Override
    public List<UserResp> getTeamUserList(@CacheKey Integer teamId) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getTeamId, teamId);
        List<SysUser> sysUserList = this.list(lambdaQueryWrapper);
        List<UserResp> userRespList = new ArrayList<>();
        for (SysUser sysUser : sysUserList) {
            UserResp userResp = getUserVo(sysUser);
            userRespList.add(userResp);
        }
        return userRespList;
    }

    @QueryCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true, preCacheSnowSlideTime = 1000, preCacheHotKeyMultiLoad = true, maxSize = 5, maxSizeStrategy = CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU)
    @Override
    public UserResp getUserInfo(@CacheKey Integer userId) {
        SysUser sysUser = getById(userId);
        Assert.notNull(sysUser, "不存在id=" + userId + "的用户");
        return getUserVo(sysUser);
    }

    private UserResp getUserVo(SysUser sysUser) {
        UserResp userResp = new UserResp();
        userResp.setUserId(sysUser.getId());
        userResp.setUserName(sysUser.getUserName());
        userResp.setPhone(sysUser.getPhone());
        userResp.setStatus(sysUser.getStatus().getName());
        userResp.setCreateUser(sysUser.getCreateUser());
        userResp.setCreateTime(sysUser.getCreateTime());
        return userResp;
    }


    @UpdateCache(type = DataType.HASH, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, preCacheSnowSlide = true)
    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public UserResp updateUserInfo(@CacheKey Integer userId, UserUpdateReq userUpdateReq) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getId, userUpdateReq.getUserId());
        updateWrapper.set(SysUser::getUserName, userUpdateReq.getUserName());
        super.update(updateWrapper);
        SysUser sysUser = super.getById(userUpdateReq.getUserId());
        Assert.notNull(sysUser, "不存在id=" + userId + "的用户");
        return getUserVo(sysUser);
    }
}
