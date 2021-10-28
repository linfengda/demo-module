package com.lfd.soa.demo.srv.support.gateway.interceptor.impl;

import cn.hutool.core.date.DateUtil;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.Constant;
import com.lfd.soa.demo.srv.support.gateway.entity.RequestSessionBO;
import com.lfd.soa.demo.srv.support.gateway.session.RequestSession;
import com.lfd.soa.demo.srv.support.gateway.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;


/**
 * 请求拦截
 *
 * @author linfengda
 * @date 2020-12-16 16:43
 */
@Slf4j
@Component
public class ApiCallInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long beginTime = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();
        MDC.put(Constant.TRACE_ID, traceId);
        RequestSessionBO requestSessionBO = RequestSessionBO.builder()
                .traceId(traceId)
                .url(request.getRequestURI())
                .method(request.getMethod())
                .requestTime(beginTime)
                .build();
        RequestSession.put(requestSessionBO);
        log.info("traceId: {}，请求路径: {}, 请求方式: {}, 请求开始时间: {}", requestSessionBO.getTraceId(), requestSessionBO.getUrl(), requestSessionBO.getMethod(), DateUtil.format(new Date(requestSessionBO.getRequestTime()), "yyyy-MM-dd HH:mm:ss"));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestSessionBO requestSessionBO = RequestSession.get();
        long endTime = System.currentTimeMillis();
        log.info("traceId: {}，请求路径: {}, 请求方式: {}, 请求结束时间: {}，请求参数: {}，响应参数: {}，请求人: {}, 请求耗时：{}ms", requestSessionBO.getTraceId(), requestSessionBO.getUrl(), requestSessionBO.getMethod(), DateUtil.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss"), JsonUtil.toJson(requestSessionBO.getRequestParams()), requestSessionBO.getResponseJson(), UserSession.getUserName(), endTime - requestSessionBO.getRequestTime());
        RequestSession.remove();
        MDC.remove(Constant.TRACE_ID);
    }
}
