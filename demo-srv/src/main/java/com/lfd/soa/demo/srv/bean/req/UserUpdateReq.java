package com.lfd.soa.demo.srv.bean.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述: 用户DTO
 *
 * @author linfengda
 * @create 2019-07-28 15:12
 */
@Data
public class UserUpdateReq {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称")
    private String userName;
}
