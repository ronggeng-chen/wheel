package org.wheel.sqlparser.api.pojo.dataset.bo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.enums.DataSetTypeEnum;
import org.wheel.sqlparser.api.pojo.base.bo.ISqlField;

import java.util.Map;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:55
 */
@Data
@Accessors(chain = true)
public class DataSetFileBO implements IDataSet {

    private String key;

    private String dataSetCode;

    private String dataSetName;

    private String description;

    private String dataSetType;

    private Map<String, ISqlField> fieldMap;

    private String timeFieldKey;

    private String fileName;

    private String suffix;

    private String syncTableAlise;

    private String syncTableCode;

    @Override
    public String getDataSetType() {
        return DataSetTypeEnum.FILE.getCode();
    }
}
