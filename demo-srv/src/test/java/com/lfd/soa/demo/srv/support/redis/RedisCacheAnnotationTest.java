package com.lfd.soa.demo.srv.support.redis;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.DemoSrvApplication;
import com.lfd.soa.demo.srv.bean.req.UserUpdateReq;
import com.lfd.soa.demo.srv.bean.resp.UserResp;
import com.lfd.soa.demo.srv.service.SysUserService;
import com.lfd.soa.demo.srv.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * redis缓存注解测试
 * @author linfengda
 * @date 2020-09-17 15:54
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSrvApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisCacheAnnotationTest {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    /**
     * 测试多个线程同时更新缓存
     * @throws Exception
     */
    @Test
    public void testMultiQueryCache() throws Exception {
        ThreadPoolTaskExecutor executor = ThreadPoolUtil.initThreadPool(10, 20, 30, "test-thread", new ThreadPoolExecutor.DiscardPolicy());
        CountDownLatch startCount = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try{
                    startCount.await();
                    UserResp userResp = sysUserService.getUserInfo(1);
                    log.info("userVO={}", JsonUtil.toJson(userResp));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
            startCount.countDown();
        }
        while(true) {
            Thread.sleep(60000);
        }
    }

    /**
     * 测试缓存空值
     * @throws Exception
     */
    @Test
    public void testNullKeyCache() throws Exception {
        log.info("{}", JsonUtil.toJson(sysUserService.getDepartmentUserList(-1)));
        log.info("{}", JsonUtil.toJson(sysUserService.getTeamUserList(-1)));
        log.info("{}", JsonUtil.toJson(sysUserService.getUserInfo(-1)));
    }

    /**
     * 测试lru缓存
     * @throws Exception
     */
    @Test
    public void testLruCache() throws Exception {
        log.info("测试lru缓存开始");
        for (int i = 1; i < 10; i++) {
            UserResp userResp = sysUserService.getUserInfo(i);
            log.info("用户查询：{}", JsonUtil.toJson(userResp));
            Set<String> userKeySet = genericRedisTemplate.hashKeys("sys:user");
            log.info("当前lru缓存：{}", JsonUtil.toJson(userKeySet));
        }
        log.info("测试lru缓存结束");
    }

    /**
     * 测试更新缓存
     * @throws Exception
     */
    @Test
    public void testUpdateCache() throws Exception {
        log.info("更新用户信息前：{}", JsonUtil.toJson(sysUserService.getUserInfo(9)));
        UserUpdateReq userUpdateReq = new UserUpdateReq();
        userUpdateReq.setUserId(9);
        userUpdateReq.setUserName("新的开始林天天");
        sysUserService.updateUserInfo(9, userUpdateReq);
        log.info("更新用户信息后：{}", JsonUtil.toJson(sysUserService.getUserInfo(9)));
    }
}
