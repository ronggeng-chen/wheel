package org.wheel.datasource.api.dto;

import lombok.Data;

@Data
public class DBFieldDTO {
    /**
     * 表逻辑名
     */
    private String tableCode;
    /**
     * 表名称
     */
    private String tableName;

    /**
     * 列逻辑名
     */
    private String fieldCode;
    /**
     * 列名称
     */
    private String fieldName;
    /**
     * java.sql.Types类型
     * package org.apache.ibatis.type;
     */
    private Integer dataType;
    /**
     * java.sql.Types类型名称
     */
    private String dataTypeName;
    /**
     *
     */
    private String javaType;
    /**
     * 列大小
     */
    private Integer fieldSize;
    /**
     * 小数位数
     */
    private Integer decimalDigits;
    /**
     * 小数基数
     */
    private Integer numPrecRadix;
    /**
     * 是否可空
     */
    private Integer nullable;
    /**
     * 自动递增
     */
    private Integer isAutoincrement;

}
