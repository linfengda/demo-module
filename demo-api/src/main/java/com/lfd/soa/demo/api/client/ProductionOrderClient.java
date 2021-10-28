package com.lfd.soa.demo.api.client;

import com.lfd.soa.demo.api.sdk.ProductionOrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p> produce order remote call </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-10-28 09:26
 */
@FeignClient(name = "${services.demo-service:demo-service}",contextId = "productionOrderClient",url = "127.0.0.1:9000",path = "")
public interface ProductionOrderClient extends ProductionOrderApi {
}
