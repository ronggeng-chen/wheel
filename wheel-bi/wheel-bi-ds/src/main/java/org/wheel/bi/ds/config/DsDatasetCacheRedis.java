package org.wheel.bi.ds.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.wheel.bi.api.config.AbstractRedisCacheOperatorExp;
import org.wheel.bi.api.constants.DsConstants;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;

/**
 *
 */
public class DsDatasetCacheRedis extends AbstractRedisCacheOperatorExp<DsDatasetDTO> {

    public DsDatasetCacheRedis(RedisTemplate<String, DsDatasetDTO> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return DsConstants.CACHE_PREFIX.DS_DATASET_CACHE_PREFIX;
    }

}
