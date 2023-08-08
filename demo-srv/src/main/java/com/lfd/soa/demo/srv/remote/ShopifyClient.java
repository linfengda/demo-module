package com.lfd.soa.demo.srv.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2023-08-06 12:50
 */
@FeignClient(name = "shopifyClient",url = "https://ur-uk-test.myshopify.com/admin",path = "")
public interface ShopifyClient {

    @PostMapping("/oauth/access_token")
    String accessToken(@RequestBody AccessTokenReq req);
}
