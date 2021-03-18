package com.lfd.soa.srv.demo.service.proxy.jdk;

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
