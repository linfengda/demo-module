package com.lfd.soa.srv.demo.service;

import com.lfd.soa.common.util.JsonUtil;
import com.lfd.soa.srv.demo.DemoApplication;
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
 * @author linfengda
 * @date 2021-03-16 19:24
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProduceOrderServiceTest {
    @Resource
    private ProduceOrderService produceOrderService;

    @Test
    public void testGet() {
        log.info("查询id=1的订单：{}", JsonUtil.toJson(produceOrderService.getById(1)));
    }
}
