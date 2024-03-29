package com.lfd.soa.demo.srv.cache;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.DemoSrvApplication;
import com.lfd.soa.demo.srv.cache.info.SysTeamCacheInfo;
import com.lfd.soa.demo.srv.cache.info.SysUserCacheInfo;
import com.lfd.soa.demo.srv.support.redis.Constant;
import com.lfd.soa.demo.srv.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 *
 * @author linfengda
 * @date 2020-09-22 23:00
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSrvApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysOrganizeCacheTest {
    @Resource
    private SysOrganizeCache sysOrganizeCache;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    /**
     * 部门缓存查询，新增，修改，删除
     * @throws Exception
     */
    @Test
    public void testDepartmentCache() throws Exception {
        sysOrganizeCache.queryDepartment(1);
        sysOrganizeCache.queryDepartments();
        log.info("缓存后，根据id查询部门：{}", JsonUtil.toJson(genericRedisTemplate.hashGet(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1")));
        log.info("缓存后，查询所有部门：{}", JsonUtil.toJson(genericRedisTemplate.setGetAll(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE)));

        sysOrganizeCache.updateDepartment(1, "技术部（新）", 0);
        log.info("更新缓存后，根据id查询部门：{}", JsonUtil.toJson(genericRedisTemplate.hashGet(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1")));
        log.info("更新缓存后，查询所有部门：{}", JsonUtil.toJson(genericRedisTemplate.setGetAll(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE)));

        sysOrganizeCache.queryDepartment(1);
        sysOrganizeCache.queryDepartments();
        sysOrganizeCache.delDepartment(1);
        log.info("删除缓存后，根据id查询部门：{}", JsonUtil.toJson(genericRedisTemplate.hashGet(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1")));
        log.info("删除缓存后，查询所有部门：{}", JsonUtil.toJson(genericRedisTemplate.setGetAll(SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE)));
    }

    /**
     * 团队缓存查询，新增，修改，删除
     * @throws Exception
     */
    @Test
    public void testTeamCache() throws Exception {
        sysOrganizeCache.queryTeamByDepartmentId(1);
        Set<SysTeamCacheInfo> sysTeamCacheDtoSet = genericRedisTemplate.setGetAll(SystemCachePrefix.SYS_ORG_TEAM_SET_CACHE + Constant.COLON + "1");
        log.info("缓存后，根据部门id查询所有团队：{}", JsonUtil.toJson(sysTeamCacheDtoSet));
    }

    /**
     * 员工缓存查询，新增，修改，删除
     * @throws Exception
     */
    @Test
    public void testUserCache() throws Exception {
        sysOrganizeCache.queryUserByTeamId(1);
        Set<SysUserCacheInfo> sysUserCacheDtoSet = genericRedisTemplate.setGetAll(SystemCachePrefix.SYS_ORG_USER_SET_CACHE + Constant.COLON + "1");
        log.info("缓存后，根据团队id查询所有员工：{}", JsonUtil.toJson(sysUserCacheDtoSet));
    }
}
