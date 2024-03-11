package org.wheel.bi.api.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.stylefeng.roses.kernel.cache.redis.AbstractRedisCacheOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.wheel.bi.api.CacheOperatorExpApi;
import org.wheel.bi.api.util.RedisExpUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * hbi
 *
 * @Author: aGeng
 * @Description: 集成Guns缓存抽象类 ，重新实现扩展接口，注入Bean配置参考Config
 * @Target:
 * @Date Created in 2023-10-23-14:45
 * @Modified By:
 */
@Slf4j
public abstract class AbstractRedisCacheOperatorExp<T> extends AbstractRedisCacheOperator<T> implements CacheOperatorExpApi<T> {

    public AbstractRedisCacheOperatorExp(RedisTemplate<String, T> redisTemplate) {
        super(redisTemplate);
    }


    @Override
    public String getCommonKeyPrefix() {
        return null;
    }

    @Override
    public void executeFlushBatch() {
        TimeInterval timer = DateUtil.timer();
        AbstractRedisCacheOperatorExp.log.info("Redis清除缓存 前缀：{} 开始", this.getCommonKeyPrefix());
        Collection allKeys = this.getAllKeys();
        this.remove(ArrayUtil.toArray(allKeys, String.class));
        AbstractRedisCacheOperatorExp.log.info("Redis清除缓存 前缀：{} 结束 耗时:{}", this.getCommonKeyPrefix(), timer.interval());
    }


    @Override
    public void executePutBatch(Map<String, T> fromMap) {
        RedisExpUtil.writeBatch(this.getRedisTemplate(), fromMap);
    }


    @Override
    public void executeFullPutBatch(Map<String, T> maps, Integer partition) {
        TimeInterval timer = DateUtil.timer();
        AbstractRedisCacheOperatorExp.log.info("Redis全量缓存 前缀：{} 开始", this.getCommonKeyPrefix());
        if (MapUtil.isEmpty(maps)) {
            AbstractRedisCacheOperatorExp.log.info("Redis全量缓存 前缀：{} 待缓存Map为空 ", this.getCommonKeyPrefix());
            return;
        }
        Collection allKeys = this.getAllKeys();
        this.remove(ArrayUtil.toArray(allKeys, String.class));
        RedisExpUtil.writeBatchTask(this.getRedisTemplate(), this.getCommonKeyPrefix(), maps, partition);
        AbstractRedisCacheOperatorExp.log.info("Redis全量缓存 前缀：{} 结束 耗时:{}", this.getCommonKeyPrefix(), timer.interval());
    }

    @Override
    public void executeIncPutBatch(Map<String, T> maps, Integer partition) {
        Collection<String> allKeys = this.getAllKeys();
        if (allKeys.size() == 0) {
            executeFullPutBatch(maps, partition);
            return;
        }
        /*if (allKeys.size() == 0 || allKeys.size() < 1000) {
            fullWriteCache(maps);
        }*/
        if (MapUtil.isEmpty(maps)) {
            AbstractRedisCacheOperatorExp.log.info("Redis增量缓存 前缀：{} 待缓存Map为空 ", this.getCommonKeyPrefix());
            return;
        }
        //注意由于指标表指标编码维护不规范，部分编码出现小写，导致CollUtil.containsAll无法定位相同指标，故使用当前类的containsAll
        if (RedisExpUtil.containsAll(allKeys, maps.keySet())) {
            AbstractRedisCacheOperatorExp.log.info("Redis增量缓存 前缀：{} 缓存Keys完全包含,不进行任何操作", this.getCommonKeyPrefix());
            return;
        }
        List<String> waitCacheKeyList = CollUtil.subtractToList(maps.keySet(), allKeys);
        if (CollUtil.isEmpty(waitCacheKeyList)) {
            AbstractRedisCacheOperatorExp.log.info("Redis增量缓存 前缀：{} 缓存Keys完全包含,不进行任何操作", this.getCommonKeyPrefix());
            return;
        }
        TimeInterval timer = DateUtil.timer();
        AbstractRedisCacheOperatorExp.log.info("Redis增量缓存 前缀：{} 开始", this.getCommonKeyPrefix());
        //注意由于指标表指标编码维护不规范，部分编码出现小写，导致CollUtil.subtractToList无法定位相同指标，故使用当前类的subtractToList
        Map<String, T> waitCacheMap = MapUtil.getAny(maps, ArrayUtil.toArray(waitCacheKeyList, String.class));
        RedisExpUtil.writeBatchTask(this.getRedisTemplate(), this.getCommonKeyPrefix(), waitCacheMap, partition);
        AbstractRedisCacheOperatorExp.log.info("Redis增量缓存 前缀：{} 结束 耗时:{}", this.getCommonKeyPrefix(), timer.interval());
    }

    @Override
    public Map<String, T> executeGetBatch(Integer partition) {
        return executeGetBatch(new ArrayList<>(this.getAllKeys()), partition);
    }

    @Override
    public Map<String, T> executeGetBatch(List<String> fromKeys, Integer partition) {
        Map<String, T> stringTMap = RedisExpUtil.readBatchTask(this.getRedisTemplate(), this.getCommonKeyPrefix(), fromKeys, partition);
        return stringTMap;
    }
}
