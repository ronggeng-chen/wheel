package org.wheel.sqlparser.api.pojo.dataset.bo;

import java.util.Map;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:50
 */
public interface IDataSet {

    String getKey();

    String getDataSetCode();

    String getDataSetName();

    String getDataSetType();

    Map<String, ISqlField> getFieldMap();

    String getTimeField();

}
