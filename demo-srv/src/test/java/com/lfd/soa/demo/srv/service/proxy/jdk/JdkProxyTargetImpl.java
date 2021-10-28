package com.lfd.soa.demo.srv.service.proxy.jdk;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-24 15:21
 */
public class JdkProxyTargetImpl implements JdkProxyTarget {
    @Override
    public void doSomething() {
        System.out.println("call doSomething()");
    }
}
