package com.lfd.soa.demo.srv.bean.entity;

import com.lfd.soa.common.bean.po.BaseEntity;
import com.lfd.soa.demo.srv.bean.enums.SysDepartmentStatusType;
import com.lfd.soa.demo.srv.bean.enums.SysDepartmentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 部门表
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
@ApiModel(value="SysDepartment对象", description="部门表")
public class SysDepartment extends BaseEntity<SysDepartment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门别名")
    private String departmentAliasName;

    @ApiModelProperty(value = "类型，1：技术；2：业务；3：行政")
    private SysDepartmentType type;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private SysDepartmentStatusType status;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;
}
