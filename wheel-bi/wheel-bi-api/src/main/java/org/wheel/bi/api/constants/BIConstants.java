package org.wheel.sqlparser.api.constants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:11
 */
public interface BIConstants {
    /**
     * 模块的名称
     */
    String MODULE_NAME = "wheel-bi";

    /**
     * 异常枚举的步进值
     */
    interface EXCEPTION_STEP_CODE {
        String BASE = "BI-BASE-";
        String CFG = "BI-CFG-";
        String DS = "BI-DATASET-";
        String EXP = "BI-EXP-";
        String VE = "BI-VE-";

    }
}
