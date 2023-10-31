package org.wheel.sqlparser.api.pojo.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.constants.SqlParserConstants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:46
 */
@Data
@Accessors(chain = true)
public class SqlDimensionFieldBO implements ISqlField {

    private String key;

    private String tableAlise;

    private String fieldCode;

    private String fieldName;

    private String description;

    private String sqlSnippet;


    @Override
    public String getFieldType() {
        return SqlParserConstants.FieldType.DIMENSION;
    }
}
