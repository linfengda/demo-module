package com.lfd.soa.demo.srv.ctrl;

import com.lfd.soa.common.bean.Result;
import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLock;
import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLockKey;
import com.lfd.soa.demo.srv.bean.req.AcceptOrderReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author linfengda
 * @date 2021-09-02 22:11
 */
@Slf4j
@RestController
@RequestMapping("/pc/test")
public class TestController {

    @BusinessLock(prefix = "order", desc = "测试业务锁test1")
    @GetMapping("/test1")
    public Result test1(@RequestParam @BusinessLockKey Integer orderId) {
        return null;
    }

    @BusinessLock(prefix = "order", desc = "测试业务锁test2")
    @PostMapping("/test2")
    public Result test2(@RequestBody AcceptOrderReq acceptOrderReq) {
        return null;
    }
}