package org.wheel.bi.api.content;

import cn.hutool.extra.spring.SpringUtil;
import org.wheel.bi.api.CacheOperatorExpApi;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;

public class DsContext {

    public static DsDatasetDTO getCacheDataset(String datasetCode) {
        CacheOperatorExpApi<DsDatasetDTO> cacheDatasetRedis = SpringUtil.getBean("dsDatasetCacheRedis", CacheOperatorExpApi.class);
        return cacheDatasetRedis.get(datasetCode);
    }

    public static DsDatasetFieldDTO getCacheDatasetField(String datasetFieldCode) {
        CacheOperatorExpApi<DsDatasetFieldDTO> cacheDatasetRedis = SpringUtil.getBean("dsDatasetFieldCacheRedis", CacheOperatorExpApi.class);
        return cacheDatasetRedis.get(datasetFieldCode);
    }


}
