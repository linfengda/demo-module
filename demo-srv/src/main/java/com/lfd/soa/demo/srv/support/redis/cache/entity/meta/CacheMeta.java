package com.lfd.soa.demo.srv.support.redis.cache.entity.meta;

import lombok.Data;

/**
 *
 * @author linfengda
 * @date 2020-07-22 10:38
 */
@Data
public class CacheMeta {
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;
}
