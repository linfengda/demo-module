package com.lfd.soa.demo.srv.cache.info;

import com.lfd.soa.demo.srv.bean.enums.SysTeamType;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 团队缓存dto
 * @author linfengda
 * @date 2020-09-23 00:12
 */
@Data
public class SysTeamCacheInfo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 项目名称
     */
    private String teamName;
    /**
     * 项目别名
     */
    private String teamAliasName;
    /**
     * 项目类型{@link SysTeamType}
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
