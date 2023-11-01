package org.wheel.sqlparser.api.pojo.dataset.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:55
 */
@Data
@Accessors(chain = true)
public class DataSetSqlBO implements IDataSet {

    private String key;

    private String dataSetCode;

    private String dataSetName;

    private List<ISqlField> fields;

    private String sql;

}
