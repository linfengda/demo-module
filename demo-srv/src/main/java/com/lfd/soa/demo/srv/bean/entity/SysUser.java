package com.lfd.soa.demo.srv.bean.entity;

import com.lfd.soa.common.bean.po.BaseEntity;
import com.lfd.soa.demo.srv.bean.enums.SysUserStatusType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 用户表
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
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser extends BaseEntity<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private SysUserStatusType status;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "所属团队id")
    private Integer teamId;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;
}
