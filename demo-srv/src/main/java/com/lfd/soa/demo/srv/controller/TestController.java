package com.lfd.soa.demo.srv.controller;

import com.lfd.soa.common.bean.resp.Resp;
import com.lfd.soa.demo.srv.bean.req.AcceptOrderReq;
import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLock;
import com.lfd.soa.demo.srv.support.redis.lock.annotation.BusinessLockKey;
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
    public Resp test1(@RequestParam @BusinessLockKey Integer orderId) {
        return null;
    }

    @BusinessLock(prefix = "order", desc = "测试业务锁test2")
    @PostMapping("/test2")
    public Resp test2(@RequestBody AcceptOrderReq acceptOrderReq) {
        return null;
    }
}
