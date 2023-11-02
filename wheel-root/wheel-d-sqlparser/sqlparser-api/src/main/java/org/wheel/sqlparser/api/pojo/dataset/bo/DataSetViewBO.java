package org.wheel.sqlparser.api.pojo.dataset.bo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.enums.DataSetTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:43
 */
@Data
@Accessors(chain = true)
public class DataSetViewBO implements IDataSet {

    private String key;

    private String dataSetCode;

    private String dataSetName;

    private String description;

    private String dataSetType;

    private Map<String, ISqlField> fieldMap;

    private String timeFieldKey;

    private List<SqlTableBO> tables;

    private List<SqlTableJoinBO> sqlTableJoins;

    private List<SqlOrderBO> orders;

    private SqlFilterBO filter;

    @Override
    public String getDataSetType() {
        return DataSetTypeEnum.VIEW.getCode();
    }

}
