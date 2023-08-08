package com.lfd.soa.demo.srv.remote;

import lombok.Data;

/**
 * <p>  </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2023-08-06 12:55
 */
@Data
public class AccessTokenReq {
    private String client_id;
    private String client_secret;
    private String code;
}
