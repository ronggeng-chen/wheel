package org.wheel.sqlparser.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.pojo.base.IBaseField;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:55
 */
@Data
@Accessors(chain = true)
public class DataSetSqlBO  implements IDataSet{

    private String key;

    private String dataSetCode;

    private String dataSetName;

    private List<IBaseField> fields;

    private String sql;

}
