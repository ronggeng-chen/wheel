package org.wheel.sqlparser.api.pojo.base;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:51
 */
public interface IBaseCondition {


    String getKey();

    String getConditionType();

    String getOperator();

    String getSqlSnippet();
}
