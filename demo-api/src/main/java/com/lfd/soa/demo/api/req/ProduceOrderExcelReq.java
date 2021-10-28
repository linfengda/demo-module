package com.lfd.soa.demo.api.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-10-28 09:31
 */
@Data
public class ProduceOrderExcelReq {
    @ApiModelProperty(value = "分页大小", required = true)
    private Long pageSize;
    @ApiModelProperty(value = "当前页", required = true)
    private Long page;
    @ApiModelProperty(value = "供应商id", required = true)
    private Integer supplierId;
}
