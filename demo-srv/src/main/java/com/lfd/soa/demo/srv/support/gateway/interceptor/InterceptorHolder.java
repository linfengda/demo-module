package com.lfd.soa.demo.srv.support.gateway.interceptor;

import com.lfd.soa.demo.srv.support.gateway.interceptor.impl.ApiCallInterceptor;
import com.lfd.soa.demo.srv.support.gateway.interceptor.impl.AuthInterceptor;
import lombok.Getter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linfengda
 * @date 2020-12-16 17:46
 */
@Getter
public class InterceptorHolder {
    public static final List<HandlerInterceptor> handlerInterceptors = new ArrayList<>();
    static {
        handlerInterceptors.add(new ApiCallInterceptor());
        handlerInterceptors.add(new AuthInterceptor());
    }
}
