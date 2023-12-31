package org.wheel.sqlparser.api.pojo.base;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:51
 */
public interface IBaseField {


    String getKey();

    String getFieldCode();

    String getFieldName();

    String getFieldType();

    String getSqlSnippet();
}
