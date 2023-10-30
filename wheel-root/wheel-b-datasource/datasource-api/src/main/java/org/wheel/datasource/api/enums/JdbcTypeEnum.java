package org.wheel.datasource.api.enums;

import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * jdbc type 枚举
 *
 * @author hufuhao
 */
public enum JdbcTypeEnum {
    /**
     * 布尔
     */
    BOOLEAN(Boolean.class, JdbcType.BOOLEAN),
    /**
     * bit
     */
    BIT(Boolean.class, JdbcType.BIT),
    /**
     * 字节
     */
    TINYINT(Byte.class, JdbcType.TINYINT),
    /**
     * 短整型
     */
    SMALLINT(Short.class, JdbcType.SMALLINT),
    /**
     * 整型
     */
    INTEGER(Integer.class, JdbcType.INTEGER),
    /**
     * 长整型
     */
    BIGINT(Long.class, JdbcType.BIGINT),
    /**
     * 单精度浮点型
     */
    FLOAT(Float.class, JdbcType.FLOAT),
    /**
     * 双精度浮点型
     */
    DOUBLE(Double.class, JdbcType.DOUBLE),
    /**
     * 单字符
     */
    CHAR(String.class, JdbcType.CHAR),
    /**
     * 字符流
     */
    CLOB(String.class, JdbcType.CLOB),
    /**
     * 字符 VARCHAR
     */
    VARCHAR(String.class, JdbcType.VARCHAR),
    /**
     * 字符 LONGVARCHAR
     */
    LONGVARCHAR(String.class, JdbcType.LONGVARCHAR),
    /**
     * 字符 NVARCHAR
     */
    NVARCHAR(String.class, JdbcType.NVARCHAR),
    /**
     * 字符 NCHAR
     */
    NCHAR(String.class, JdbcType.NCHAR),
    /**
     * 字符 NCLOB
     */
    NCLOB(String.class, JdbcType.NCLOB),
    /**
     * 数组
     */
    ARRAY(Object.class, JdbcType.ARRAY),

    /**
     * BigDecimal
     */
    REAL(BigDecimal.class, JdbcType.REAL),
    /**
     * DECIMAL
     */
    DECIMAL(BigDecimal.class, JdbcType.DECIMAL),
    /**
     * NUMERIC
     */
    NUMERIC(BigDecimal.class, JdbcType.NUMERIC),
    /**
     * blob
     */
    BLOB(Byte[].class, JdbcType.BLOB),
    /**
     * LONGVARBINARY
     */
    LONGVARBINARY(Byte[].class, JdbcType.LONGVARBINARY),
    /**
     * OTHER
     */
    OTHER(Object.class, JdbcType.OTHER),
    /**
     * 日期
     */
    DATE(Date.class, JdbcType.DATE),
    /**
     * 时间
     */
    TIME(Date.class, JdbcType.TIME),
    /**
     * TIMESTAMP
     */
    TIMESTAMP(Date.class, JdbcType.TIMESTAMP),
    /**
     * SQLxml
     */
    SQLXML(String.class, JdbcType.SQLXML);

    private Class javaType;

    private JdbcType jdbcType;

    private Integer sqlType;

    JdbcTypeEnum(Class javaType, JdbcType jdbcType) {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
        this.sqlType = jdbcType.TYPE_CODE;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Integer getSqlType() {
        return sqlType;
    }

    public void setSqlType(Integer sqlType) {
        this.sqlType = sqlType;
    }

    public static JdbcTypeEnum getEnum(Integer jdbcType) {
        for (JdbcTypeEnum jdbcTypeEnum : JdbcTypeEnum.values()) {
            if (jdbcTypeEnum.getSqlType().equals(jdbcType)) {
                return jdbcTypeEnum;
            }
        }
        return null;
    }
}
    



