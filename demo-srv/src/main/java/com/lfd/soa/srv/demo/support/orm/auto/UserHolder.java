package com.lfd.soa.srv.demo.support.orm.auto;

import org.springframework.util.Assert;

/**
 * 描述: 获取当前操作人
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public final class UserHolder {
    private static UserAware userAware;

    /**
     * 需要调用这个方法初始化Session获取途径
     *
     * @param userAware aware user info
     */
    public static void init(UserAware userAware) {
        Assert.notNull(userAware, "userAware实例不能为空");
        UserHolder.userAware = userAware;
    }

    /**
     * 获取当前uid
     *
     * @return 当前uid
     */
    public static String getCurrentUid() {
        Assert.notNull(userAware, "userAware实例不能为空");
        return userAware.getCurrentUid();
    }

    /**
     * 获取当前uName
     *
     * @return 当前uid
     */
    public static String getCurrentUName() {
        Assert.notNull(userAware, "userAware实例不能为空");
        return userAware.getCurrentUName();
    }
}
