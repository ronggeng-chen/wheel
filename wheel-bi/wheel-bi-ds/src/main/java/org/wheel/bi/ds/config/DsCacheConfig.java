package org.wheel.bi.ds.config;

import cn.stylefeng.roses.kernel.cache.redis.util.CreateRedisTemplateUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.wheel.bi.api.CacheOperatorExpApi;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;

@Configuration
public class DsCacheConfig {

    @Bean
    @ConditionalOnMissingBean(name = "dsDatasetCacheRedis")
    public CacheOperatorExpApi<DsDatasetDTO> dsDatasetCacheRedis(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, DsDatasetDTO> cache = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new DsDatasetCacheRedis(cache);
    }

    @Bean
    @ConditionalOnMissingBean(name = "dsDatasetFieldCacheRedis")
    public CacheOperatorExpApi<DsDatasetFieldDTO> dsDatasetFieldCacheRedis(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, DsDatasetFieldDTO> cache = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new DsDatasetFieldCacheRedis(cache);
    }

}
