package com.lfd.soa.demo.srv.controller;

import com.lfd.soa.common.bean.resp.Resp;
import com.lfd.soa.demo.srv.remote.AccessTokenReq;
import com.lfd.soa.demo.srv.remote.ShopifyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2023-08-06 12:36
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private ShopifyClient shopifyClient;

    @GetMapping("/callback")
    public Resp<?> callback(@RequestParam String code, @RequestParam String hmac, @RequestParam String host, @RequestParam String shop, @RequestParam String state, @RequestParam String timestamp) {
        log.info("code=" + code + ",hmac=" + hmac + ",host=" + host + ",shop=" + shop + ",state=" + state + ",timestamp=" + timestamp);
        AccessTokenReq req = new AccessTokenReq();
        req.setClient_id("627d3f6b9733bc02b2e3a9a5270b1ff9");
        req.setClient_secret("fb0b8165c353bfee56cd161dbcd7ddd1");
        req.setCode(code);
        String resp = shopifyClient.accessToken(req);
        log.info("resp=" + resp);
        return new Resp<>(resp);
        //return new Resp<>(code);
    }
}
