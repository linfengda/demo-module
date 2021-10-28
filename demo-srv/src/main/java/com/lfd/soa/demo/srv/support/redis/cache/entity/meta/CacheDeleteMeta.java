package com.lfd.soa.demo.srv.support.redis.cache.entity.meta;

import lombok.Data;

import java.util.List;

/**
 *
 * @author linfengda
 * @date 2020-07-22 18:39
 */
@Data
public class CacheDeleteMeta extends CacheMeta {
    /**
     * 删除列表
     */
    private List<CacheMethodMeta> deleteMetas;
}
