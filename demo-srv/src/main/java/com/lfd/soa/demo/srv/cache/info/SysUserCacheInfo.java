package com.lfd.soa.demo.srv.cache.info;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户缓存dto
 * @author linfengda
 * @date 2020-09-23 22:34
 */
@Data
public class SysUserCacheInfo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 项目id
     */
    private Integer teamId;
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
