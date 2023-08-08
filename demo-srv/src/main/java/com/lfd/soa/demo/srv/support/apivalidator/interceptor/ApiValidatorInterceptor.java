package com.lfd.soa.demo.srv.support.apivalidator.interceptor;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.srv.support.apivalidator.ApiParameterValidator;
import com.lfd.soa.demo.srv.support.gateway.entity.RequestSessionBO;
import com.lfd.soa.demo.srv.support.gateway.session.RequestSession;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述: api方法拦截器
 *
 * @author linfengda
 * @create 2020-03-24 15:16
 */
@Slf4j
public class ApiValidatorInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        initRequestParams(invocation.getMethod().getParameters(), invocation.getArguments());
        ApiParameterValidator apiParameterValidator = new ApiParameterValidator();
        apiParameterValidator.validateControllerMethodParameter(invocation);
        Object result = invocation.proceed();
        initResponseJson(result);
        return result;
    }

    /**
     * 从请求中获取参数信息
     *
     * @param parameters    请求方法
     * @param arguments     请求参数
     */
    private void initRequestParams(Parameter[] parameters, Object[] arguments) {
        if (null == parameters || 0 == parameters.length) {
            return;
        }
        RequestSessionBO requestSessionBO = RequestSession.get();
        if (null == requestSessionBO) {
            return;
        }
        Map<String, Object> params = new HashMap<>(8);
        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName();
            Object paramObj = arguments[i];
            if (null == paramObj) {
                continue;
            }
            if (paramObj instanceof HttpServletRequest) {
                params.put(paramName, paramObj.toString());
                continue;
            }
            if (paramObj instanceof HttpServletResponse) {
                params.put(paramName, paramObj.toString());
                continue;
            }
            if (paramObj instanceof MultipartFile) {
                params.put(paramName, ((MultipartFile) paramObj).getOriginalFilename());
                continue;
            }
            if (paramObj instanceof List) {
                List<?> list = (List<?>) paramObj;
                if (!CollectionUtils.isEmpty(list) && list.get(0) instanceof MultipartFile) {
                    List<String> files = list.stream().map(m -> ((MultipartFile) m).getOriginalFilename()).collect(Collectors.toList());
                    params.put(paramName, JsonUtil.toJson(files));
                    continue;
                }
            }
            params.put(paramName, JsonUtil.toJson(paramObj));
        }
        requestSessionBO.setRequestParams(JsonUtil.toJson(params));
    }

    /**
     * 从响应中中获取报文信息
     *
     * @param result 响应
     */
    private void initResponseJson(Object result) {
        RequestSessionBO requestInfoBO = RequestSession.get();
        if (null == requestInfoBO) {
            return;
        }
        requestInfoBO.setResponseJson(JsonUtil.toJson(result));
    }
}