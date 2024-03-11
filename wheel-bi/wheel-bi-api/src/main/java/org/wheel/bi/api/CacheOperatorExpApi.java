package org.wheel.bi.api;

import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import org.wheel.bi.api.util.RedisExpUtil;

import java.util.List;
import java.util.Map;

/**
 * hbi
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-10-23-14:37
 * @Modified By:
 */
public interface CacheOperatorExpApi<T> extends CacheOperatorApi<T> {
    @Override
    default String calcKey(String keyParam) {
        return RedisExpUtil.getPrefix(this.getCommonKeyPrefix(), keyParam);
    }

    void executeFlushBatch();

    default void executeFullPutBatch(Map<String, T> maps) {
        executeFullPutBatch(maps, 1000);
    }

    void executeFullPutBatch(Map<String, T> maps, Integer partition);

    default void executeIncPutBatch(Map<String, T> maps) {
        executeIncPutBatch(maps, 500);
    }

    void executeIncPutBatch(Map<String, T> maps, Integer partition);

    void executePutBatch(Map<String, T> fromMap);

    /**
     * 管道批量查询key
     *
     * @return
     */
    default Map<String, T> executeGetBatch() {
        return executeGetBatch(1000);
    }

    Map<String, T> executeGetBatch(Integer partition);

    Map<String, T> executeGetBatch(List<String> fromKeys, Integer partition);

}
