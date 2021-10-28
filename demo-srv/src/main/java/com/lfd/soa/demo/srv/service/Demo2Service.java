package com.lfd.soa.demo.srv.service;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author linfengda
 * @date 2021-05-08 11:13
 */
@Component
public class Demo2Service {

    @Resource
    private Demo1Service demo1Service;
}
