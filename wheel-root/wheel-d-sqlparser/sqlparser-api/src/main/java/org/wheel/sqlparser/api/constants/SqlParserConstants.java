package org.wheel.sqlparser.api.constants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:11
 */
public interface SqlParserConstants {
    /**
     * auth模块的名称
     */
    String MODULE_NAME = "wheel-d-sqlparser";

    /**
     * 异常枚举的步进值
     */
    interface EXCEPTION_STEP_CODE{
        String BASE = "1010";
        String VIEW = "1020";
        String FILE = "1030";
        String SQL = "1040";
    }

    interface FieldType{
        /**
         * 维度
         */
        String DIMENSION = "dimension";
        /**
         * 度量
         */
        String MEASURE = "measure";

    }

    interface ConditionType{
        /**
         * 正常条件 A字段 = Value
         */
        String NORMAL = "normal";
        /**
         * 计算条件 A表达式 = B表达式 or Value
         */
        String COMPUTE = "compute";

    }
}
