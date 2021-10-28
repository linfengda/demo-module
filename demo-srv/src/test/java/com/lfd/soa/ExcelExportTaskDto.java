package com.lfd.soa;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 导出任务dto </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-10-28 11:48
 */
@Data
public class ExcelExportTaskDto {

    @ApiModelProperty(value = "任务id", required = true)
    private Long taskId;

    @ApiModelProperty(value = "用户id", required = true)
    private Long uid;

    @ApiModelProperty(value = "查询服务", required = true)
    private String service;

    @ApiModelProperty(value = "查询接口", required = true)
    private String url;

    @ApiModelProperty(value = "查询条件", required = true)
    private String params;

    @ApiModelProperty(value = "分页大小", required = true)
    private Long pageSize;

    @ApiModelProperty(value = "总页数", required = true)
    private Long totalPage;
}
