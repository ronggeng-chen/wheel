package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigViewTableDTO {
    /**
     * 主表标识
     */
    private Boolean mainFlag;
    /**
     * 表别名
     */
    private String tableAlias;
    /**
     * 表编码
     */
    private String tableCode;
    /**
     * 表名称
     */
    private String tableName;
}
