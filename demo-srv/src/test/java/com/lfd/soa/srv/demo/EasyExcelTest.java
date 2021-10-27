package com.lfd.soa.srv.demo;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lfd.soa.srv.demo.bean.entity.ProduceOrder;
import com.lfd.soa.srv.demo.bean.type.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
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

    @Test
    public void templateWrite() throws Exception {
        long t1 = System.currentTimeMillis();
        String templateFileName = "C:\\Users\\patpat\\Desktop\\template.xlsx";
        String fileName = "C:\\Users\\patpat\\Desktop\\demo.xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, ProduceOrder.class).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
            // 写入30w行数据
            for (int i = 0; i < 1; i++) {
                // 每次写入1w行数据
                List<ProduceOrderExcel> data = data(String.format("O-%02d", i));
                excelWriter.fill(data, writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
            log.info("导出excel完成，耗时：{}ms", System.currentTimeMillis()-t1);
        }
    }

    private List<ProduceOrderExcel> data(String prefix) throws Exception {
        List<ProduceOrderExcel> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProduceOrderExcel produceOrderExcel = new ProduceOrderExcel();
            produceOrderExcel.setState(OrderState.producing.getName());
            produceOrderExcel.setOrderNumber(prefix + i);
            produceOrderExcel.setSku("19ML003019ML000000010255167");
            produceOrderExcel.setReferenceSku("19ML003019ML000000010255167");
            produceOrderExcel.setPurchasePrice(new BigDecimal("27.66"));
            produceOrderExcel.setReferenceImageUrl(new URL("https://image.yfsassets.com/fit-in/950x950/origin/product/000484000632/5e8d9e82df5b9.jpg"));
            produceOrderExcel.setSupplier("广州志茂服饰有限公司");
            produceOrderExcel.setSupplierId(123);
            produceOrderExcel.setMerchandiser("林丰达");
            data.add(produceOrderExcel);
        }
        return data;
    }
}
