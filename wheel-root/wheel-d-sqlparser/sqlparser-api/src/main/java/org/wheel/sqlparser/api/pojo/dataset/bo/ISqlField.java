package org.wheel.sqlparser.api.pojo.dataset.bo;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:51
 */
public interface ISqlField {


    String getKey();

    String getFieldCode();

    String getFieldName();

    String getFieldType();

    String getSqlSnippet();
}
