package com.lfd.soa.demo.srv.support.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块类型
 * @author linfengda
 * @date 2020-09-23 11:40
 */
@Getter
@AllArgsConstructor
public enum ModuleType {
    /**
     * pc端
     */
    PC,
    /**
     * 公众号
     */
    PUBLIC_ACCOUNT;
}
