package org.wheel.sqlparser.api.pojo.base.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:00
 */
@Data
@Accessors(chain = true)
public class SqlTableBO {

    private String key;

    private Boolean isMain;

    private String tableAlias;

    private String tableCode;

    private String tableName;

    private String description;
}
