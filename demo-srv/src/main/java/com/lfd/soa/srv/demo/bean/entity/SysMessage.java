package com.lfd.soa.srv.demo.bean.entity;

import com.lfd.soa.common.bean.po.BaseIncrementEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * mq消息表
 * </p>
 *
 * @author linfengda
 * @since 2021-04-25
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysMessage对象", description="mq消息表")
public class SysMessage extends BaseIncrementEntity<SysMessage> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "请求报文")
    private String message;

    @ApiModelProperty(value = "mq服务")
    private String service;

    @ApiModelProperty(value = "mq队列")
    private String queue;

    @ApiModelProperty(value = "消息类型，PRODUCER：发送，CONSUMER：接收")
    private String type;

    @ApiModelProperty(value = "发送状态(0:等待发送，1：发送成功，2：死亡)")
    private Integer sendState;

    @ApiModelProperty(value = "消费状态(0:等待消费，1：消费成功，2：死亡)")
    private Integer consumeState;

    @ApiModelProperty(value = "重试次数")
    private Integer tryCount;

    @ApiModelProperty(value = "重试时间")
    private Date tryTime;

    @ApiModelProperty(value = "处理成功时间")
    private Date successTime;

    @ApiModelProperty(value = "执行错误信息")
    private String errorLog;

    @ApiModelProperty(value = "创建人")
    private String createUname;

    @ApiModelProperty(value = "更新人")
    private String updateUname;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
