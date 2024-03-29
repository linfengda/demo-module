package com.lfd.soa.demo.srv.cache.info;

import com.lfd.soa.demo.srv.bean.enums.SysDepartmentType;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 部门缓存dto
 * @author linfengda
 * @date 2020-07-27 17:26
 */
@Data
public class SysDepartmentCacheInfo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 部门别名
     */
    private String departmentAliasName;
    /**
     * 部门类型{@link SysDepartmentType}
     */
    private Integer type;
    /**
     * 状态：0启用，1停用
     */
    private Integer status;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 修改时间
     */
    private Timestamp updateTime;
    /**
     * 是否删除 {@link com.lfd.soa.common.bean.type.DeleteTag}
     */
    private Integer isDelete;
    /**
     * 版本号
     */
    private Integer version;
}
