package com.lfd.soa.demo.srv;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.demo.api.client.ProductionOrderClient;
import com.lfd.soa.demo.api.req.ProduceOrderExcelReq;
import com.lfd.soa.demo.api.resp.ProduceOrderExcelResp;
import com.lfd.soa.demo.srv.bean.entity.ProduceOrder;
import com.lfd.soa.demo.srv.bean.type.OrderState;
import com.lfd.soa.demo.srv.service.ProduceOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> easyExcel测试类 </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-10-27 14:57
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSrvApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EasyExcelTest {
    @Resource
    private ProduceOrderService produceOrderService;
    @Resource
    private ProductionOrderClient productionOrderClient;


    @Test
    public void insertData() throws Exception {
        List<ProduceOrder> produceOrderList = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            ProduceOrder produceOrder = new ProduceOrder();
            produceOrder.setState(OrderState.producing);
            produceOrder.setOrderNumber(String.format("O-20211028-%06d", i));
            produceOrder.setSku("19ML003019ML000000010255167");
            produceOrder.setReferenceSku("19ML003019ML000000010255167");
            produceOrder.setPurchasePrice(new BigDecimal("27.66"));
            produceOrder.setReferenceImageUrl("https://image.yfsassets.com/fit-in/950x950/origin/product/000484000632/5e8d9e82df5b9.jpg");
            produceOrder.setSupplier("广州志茂服饰有限公司");
            produceOrder.setSupplierId(123);
            produceOrder.setMerchandiser("林丰达");
            produceOrder.setAcceptTime(DateUtil.date());
            produceOrderList.add(produceOrder);
        }
        produceOrderService.saveBatch(produceOrderList);
    }

    @Test
    public void testExcelQuery() throws Exception {
        long t1 = System.currentTimeMillis();
        ExcelExportTaskDto excelExportTaskDto = getExcelExportTaskDto();
        ProduceOrderExcelReq produceOrderExcelReq = JsonUtil.readValue(excelExportTaskDto.getParams(), ProduceOrderExcelReq.class);
        produceOrderExcelReq.setPageSize(1000L);
        for (long i = 1; i <= 10; i++) {
            produceOrderExcelReq.setPage(i);
            List<ProduceOrderExcelResp> data = productionOrderClient.queryExport(produceOrderExcelReq);
            log.info("查询excel导出数据：{}", JsonUtil.toJson(data));
        }
        log.info("查询excel导出数据完成，耗时：{}ms", System.currentTimeMillis()-t1);
    }

    @Test
    public void testExcelTemplateWrite() throws Exception {
        long t1 = System.currentTimeMillis();
        // 扫描导出任务
        ExcelExportTaskDto excelExportTaskDto = getExcelExportTaskDto();
        String templateFileName = "C:\\Users\\patpat\\Desktop\\template.xlsx";
        String fileName = "C:\\Users\\patpat\\Desktop\\";
        ExcelWriter excelWriter = null;
        try {
            // 执行导出任务
            excelWriter = EasyExcel.write(fileName + System.currentTimeMillis() + ".xlsx", ProduceOrder.class).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
            ProduceOrderExcelReq produceOrderExcelReq = JsonUtil.readValue(excelExportTaskDto.getParams(), ProduceOrderExcelReq.class);
            produceOrderExcelReq.setPageSize(excelExportTaskDto.getPageSize());
            for (long i = 1; i <= excelExportTaskDto.getTotalPage(); i++) {
                produceOrderExcelReq.setPage(i);
                List<ProduceOrderExcelResp> data = productionOrderClient.queryExport(produceOrderExcelReq);
                if (CollectionUtils.isEmpty(data)) {
                    break;
                }
                excelWriter.fill(data, writeSheet);
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            // 上传excel文件
            // 更新导出结果
            log.info("导出excel完成，耗时：{}ms", System.currentTimeMillis()-t1);
        }
    }

    private ExcelExportTaskDto getExcelExportTaskDto() {
        ExcelExportTaskDto excelExportTaskDto = new ExcelExportTaskDto();
        excelExportTaskDto.setTaskId(1L);
        excelExportTaskDto.setUid(123L);
        excelExportTaskDto.setService("demo-service");
        excelExportTaskDto.setUrl("/v1/productionOrder/queryExport");
        ProduceOrderExcelReq produceOrderExcelReq = new ProduceOrderExcelReq();
        produceOrderExcelReq.setSupplierId(1);
        excelExportTaskDto.setParams(JsonUtil.toJson(produceOrderExcelReq));
        excelExportTaskDto.setPageSize(1000L);
        excelExportTaskDto.setTotalPage(10L);
        return excelExportTaskDto;
    }
}
