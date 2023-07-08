package com.lfd.soa.demo.api.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2023-07-08 21:11
 */
@Data
public class ProduceOrderDetailResp {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "生产状态")
    private String state;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "sku")
    private String sku;

    @ApiModelProperty(value = "参考sku")
    private String referenceSku;

    @ApiModelProperty(value = "生产单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "参考图片地址")
    private URL referenceImageUrl;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "供应商id")
    private Integer supplierId;

    @ApiModelProperty(value = "跟单员")
    private String merchandiser;
}
