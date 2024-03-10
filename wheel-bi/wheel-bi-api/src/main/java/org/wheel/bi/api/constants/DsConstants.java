package org.wheel.bi.api.constants;

public interface DsConstants {
    /**
     * 模块的名称
     */
    String MODULE_NAME = "wheel-bi-ds";

    /**
     * 异常枚举的步进值
     */
    interface EXCEPTION_STEP_CODE {

        String DATASET = "dataset";

    }

    interface CACHE_PREFIX {
        String DS_DATASET_CACHE_PREFIX = "ds_dataset";

        String DS_DATASET_FIELD_CACHE_PREFIX = "ds_dataset_field";
    }

}
