package com.lfd.soa.demo.srv.bean.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author linfengda
 * @date 2020-12-22 11:37
 */
@AllArgsConstructor
@Getter
public enum SysDepartmentStatusType {
    /**
     * 0：启用
     */
    YES(0, "启用"),
    /**
     * 1：停用
     */
    NO(1, "停用");

    private Integer code;
    private String name;

    public static SysDepartmentStatusType getType(Integer state) {
        for (SysDepartmentStatusType value : values()) {
            if (value.getCode().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
