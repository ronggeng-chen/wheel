package org.wheel.bi.ds.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.wheel.bi.api.config.AbstractRedisCacheOperatorExp;
import org.wheel.bi.api.constants.DsConstants;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;

/**
 *
 */
public class DsDatasetFieldCacheRedis extends AbstractRedisCacheOperatorExp<DsDatasetFieldDTO> {

    public DsDatasetFieldCacheRedis(RedisTemplate<String, DsDatasetFieldDTO> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return DsConstants.CACHE_PREFIX.DS_DATASET_FIELD_CACHE_PREFIX;
    }

}
