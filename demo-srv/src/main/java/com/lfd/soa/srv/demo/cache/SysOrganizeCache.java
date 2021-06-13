package com.lfd.soa.srv.demo.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lfd.soa.srv.demo.cache.info.SysDepartmentCacheInfo;
import com.lfd.soa.srv.demo.cache.info.SysTeamCacheInfo;
import com.lfd.soa.srv.demo.cache.info.SysUserCacheInfo;
import com.lfd.soa.srv.demo.bean.entity.SysDepartment;
import com.lfd.soa.srv.demo.bean.entity.SysTeam;
import com.lfd.soa.srv.demo.bean.entity.SysUser;
import com.lfd.soa.srv.demo.service.SysDepartmentService;
import com.lfd.soa.srv.demo.service.SysTeamService;
import com.lfd.soa.srv.demo.service.SysUserService;
import com.lfd.soa.srv.demo.support.redis.cache.annotation.*;
import com.lfd.soa.srv.demo.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 系统组织缓存服务
 * @author linfengda
 * @date 2020-07-27 23:32
 */
@Component
@Slf4j
public class SysOrganizeCache {
    @Resource
    private SysDepartmentService sysDepartmentService;
    @Resource
    private SysTeamService sysTeamService;
    @Resource
    private SysUserService sysUserService;


    @QueryCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public SysDepartmentCacheInfo queryDepartment(@CacheKey Integer departmentId) throws Exception {
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheInfo sysDepartmentCacheInfo = new SysDepartmentCacheInfo();
        BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheInfo);
        return sysDepartmentCacheInfo;
    }

    @DeleteCache(caches = {@Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true)})
    @UpdateCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Transactional(rollbackFor = Exception.class)
    public SysDepartmentCacheInfo updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        LambdaUpdateWrapper<SysDepartment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDepartment::getDepartmentName, departmentName);
        updateWrapper.eq(SysDepartment::getStatus, status);
        sysDepartmentService.update(updateWrapper);
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheInfo sysDepartmentCacheInfo = new SysDepartmentCacheInfo();
        BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheInfo);
        return sysDepartmentCacheInfo;
    }

    @DeleteCache(
            caches = {
            @Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true),
            @Cache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)})
    @Transactional(rollbackFor = Exception.class)
    public void delDepartment(@CacheKey Integer departmentId) throws Exception {
        sysDepartmentService.removeById(departmentId);
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysDepartmentCacheInfo> queryDepartments() throws Exception {
        List<SysDepartment> sysDepartmentList = sysDepartmentService.list();
        Set<SysDepartmentCacheInfo> sysDepartmentCacheInfoSet = new HashSet<>();
        for (SysDepartment sysDepartment : sysDepartmentList) {
            SysDepartmentCacheInfo sysDepartmentCacheInfo = new SysDepartmentCacheInfo();
            BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheInfo);
            sysDepartmentCacheInfoSet.add(sysDepartmentCacheInfo);
        }
        return sysDepartmentCacheInfoSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysTeamCacheInfo> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        LambdaQueryWrapper<SysTeam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTeam::getDepartmentId, departmentId);
        List<SysTeam> sysTeamList = sysTeamService.list(queryWrapper);
        Set<SysTeamCacheInfo> sysTeamCacheInfoSet = new HashSet<>();
        for (SysTeam sysTeam : sysTeamList) {
            SysTeamCacheInfo sysTeamCacheInfo = new SysTeamCacheInfo();
            BeanUtils.copyProperties(sysTeam, sysTeamCacheInfo);
            sysTeamCacheInfoSet.add(sysTeamCacheInfo);
        }
        return sysTeamCacheInfoSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_USER_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysUserCacheInfo> queryUserByTeamId(@CacheKey Integer teamId) throws Exception {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getTeamId, teamId);
        List<SysUser> sysUserList = sysUserService.list(queryWrapper);
        Set<SysUserCacheInfo> sysUserCacheInfoSet = new HashSet<>();
        for (SysUser sysUser : sysUserList) {
            SysUserCacheInfo sysUserCacheInfo = new SysUserCacheInfo();
            BeanUtils.copyProperties(sysUser, sysUserCacheInfo);
            sysUserCacheInfoSet.add(sysUserCacheInfo);
        }
        return sysUserCacheInfoSet;
    }
}
