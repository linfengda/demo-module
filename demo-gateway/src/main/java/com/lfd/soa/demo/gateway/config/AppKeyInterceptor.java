package com.lfd.soa.demo.gateway.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 *
 * @author linfengda
 * @date 2020-09-28 00:20
 */
public class AppKeyInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("appKey", "app-mes");
    }
}
