package com.lfd.soa.srv.demo.remote;

import com.lfd.soa.srv.demo.DemoSrvApplication;
import com.lfd.soa.srv.demo.bean.req.BomMaterialQueryReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *
 * @author linfengda
 * @date 2020-09-22 15:11
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSrvApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlmRemoteTest {
    @Resource
    private PlmRemote plmRemote;

    /**
     * 测试feign post请求
     */
    @Test
    public void testGetOrderBigBomMaterials() {
        BomMaterialQueryReq bomMaterialQueryReq = new BomMaterialQueryReq();
        bomMaterialQueryReq.setSku("DK1234");
        bomMaterialQueryReq.setVersion(1);
        log.info("请求plm订单大货物料信息，返回：{}", plmRemote.getOrderBigBomMaterials(bomMaterialQueryReq));
    }

    /**
     * 测试feign get请求
     */
    @Test
    public void getMaterialPrice() {
        log.info("请求plm物料单价，返回：{}", plmRemote.getMaterialPrice("DK1234"));
    }
}
