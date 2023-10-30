package org.wheel.datasource.api.dto;

import lombok.Data;

@Data
public class DBTableDTO {
    /**
     * 表类型 表 or 视图
     */
    private String tableType;
    /**
     * 表逻辑名
     */
    private String tableCode;
    /**
     * 表名称
     */
    private String tableName;

}
