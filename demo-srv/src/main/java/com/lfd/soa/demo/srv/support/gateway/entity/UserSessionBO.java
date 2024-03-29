package com.lfd.soa.demo.srv.support.gateway.entity;

import lombok.Builder;
import lombok.Data;

/**
 * mes用户session信息
 * @author linfengda
 * @date 2020-12-15 22:37
 */
@Data
@Builder
public class UserSessionBO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * token
     */
    private String authorization;
}
