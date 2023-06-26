package com.lfd.soa.demo.srv.bean.entity;

import com.lfd.soa.common.bean.po.BaseEntity;
import com.lfd.soa.demo.srv.bean.enums.OrderState;
import com.lfd.soa.demo.srv.support.mybatis.annotation.BizTimeField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 生产大货订单表
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
@ApiModel(value="ProduceOrder对象", description="生产大货订单表")
public class ProduceOrder extends BaseEntity<ProduceOrder> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生产状态")
    private OrderState state;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "sku")
    private String sku;

    @ApiModelProperty(value = "参考sku")
    private String referenceSku;

    @ApiModelProperty(value = "生产单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "参考图片地址")
    private String referenceImageUrl;

    @ApiModelProperty(value = "面料品类")
    private Integer materialTypeEnum;

    @ApiModelProperty(value = "三级分类")
    private Integer threeCategoryId;

    @ApiModelProperty(value = "0：无特殊工艺，1：特殊工艺，")
    private Integer specialTechnologyTag;

    @ApiModelProperty(value = "特殊工艺描述")
    private String specialTechnologyText;

    @ApiModelProperty(value = "是否首单，0：否，1：是")
    private Integer firstOrder;

    @ApiModelProperty(value = "是否紧急，0：否，1：是")
    private Integer urgent;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "供应商id")
    private Integer supplierId;

    @ApiModelProperty(value = "跟单员")
    private String merchandiser;

    @BizTimeField
    @ApiModelProperty(value = "接单时间")
    private Date acceptTime;

    @BizTimeField
    @ApiModelProperty(value = "裁剪时间")
    private Date cutTime;

    @BizTimeField
    @ApiModelProperty(value = "车缝时间")
    private Date sewTime;

    @BizTimeField
    @ApiModelProperty(value = "后整时间")
    private Date sortTime;

    @BizTimeField
    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;
}
