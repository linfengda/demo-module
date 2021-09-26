package com.lfd.soa.srv.demo.support.redis.lock.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p> 开启业务锁 </p>
 *
 * @author linfengda
 * @copyright Copyright (C) 2021 PatPat, Inc. All rights reserved. <br>
 * @company 深圳盈富斯科技有限公司
 * @date 2021-09-18 16:53
 */
public class BusinessLockConfigSelector implements ImportSelector {

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        return new String[]{BusinessLockConfig.class.getName()};
    }
}
