package com.lfd.soa.demo.srv.bean.req;

import com.lfd.soa.common.bean.req.BasePageParamReq;
import lombok.Data;

/**
 * 描述: 用户分页查询req
 *
 * @author linfengda
 * @create 2019-12-19 13:27
 */
@Data
public class UserPageParamReq extends BasePageParamReq {
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 用户名称
     */
    private String userName;
}
