package com.lfd.soa.demo.srv.bean.entity;

import com.lfd.soa.common.bean.po.BaseEntity;
import com.lfd.soa.demo.srv.bean.enums.SysTeamStatusType;
import com.lfd.soa.demo.srv.bean.enums.SysTeamType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 团队表
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysTeam对象", description="团队表")
public class SysTeam extends BaseEntity<SysTeam> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    private String teamName;

    @ApiModelProperty(value = "项目别名")
    private String teamAliasName;

    @ApiModelProperty(value = "类型，1：技术；2：业务；3：行政")
    private SysTeamType type;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private SysTeamStatusType status;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;
}
