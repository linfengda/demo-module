package com.lfd.soa.srv.demo.support.apivalidator.interceptor;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.srv.demo.support.apivalidator.ApiParameterValidator;
import com.lfd.soa.srv.demo.support.gateway.entity.RequestSessionBO;
import com.lfd.soa.srv.demo.support.gateway.session.RequestSession;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
        Object[] arguments = invocation.getArguments();
        initRequestParams(arguments);
        ApiParameterValidator apiParameterValidator = new ApiParameterValidator();
        apiParameterValidator.validateControllerMethodParameter(invocation);
        Object result = invocation.proceed();
        initResponseJson(result);
        return result;
    }

    /**
     * 从请求中获取参数信息
     *
     * @param arguments 请求参数
     */
    private void initRequestParams(Object[] arguments) {
        if (null == arguments || 0 == arguments.length) {
            return;
        }
        RequestSessionBO requestInfoBO = RequestSession.get();
        if (null == requestInfoBO) {
            return;
        }
        List<String> params = new ArrayList<>();
        for (Object arg : arguments) {
            if (arg instanceof MultipartFile) {
                params.add(((MultipartFile) arg).getOriginalFilename());
                continue;
            }
            if (arg instanceof List) {
                List<?> list = (List<?>) arg;
                if (!CollectionUtils.isEmpty(list) && list.get(0) instanceof MultipartFile) {
                    List<String> files = list.stream().map(m -> ((MultipartFile) m).getOriginalFilename()).collect(Collectors.toList());
                    params.add(JsonUtil.toJson(files));
                    continue;
                }
            }
            params.add(JsonUtil.toJson(arg));
        }
        requestInfoBO.setRequestParams(params);
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