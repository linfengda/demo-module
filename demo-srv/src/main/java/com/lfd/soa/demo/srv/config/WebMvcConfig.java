package com.lfd.soa.demo.srv.config;

import com.lfd.soa.demo.srv.support.gateway.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 *
 * @author linfengda
 * @date 2020-09-21 16:45
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口登录权限检测拦截器--拦截所有请求
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }
}
