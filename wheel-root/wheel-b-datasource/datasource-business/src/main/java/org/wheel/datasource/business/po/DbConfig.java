package org.wheel.datasource.business.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("db_config")
public class DbConfig {
    /**
     * id
     */
    @TableId(value = "db_id",type = IdType.ASSIGN_ID)
    private Long dbId;
    /**
     * 数据源代码
     */
    @TableField(value = "db_code")
    private String dbCode;
    /**
     * 数据库类型 oracle sqlserver mysql 等等
     */
    @TableField(value = "db_type")
    private String dbType;
    /**
     * 数据源名称
     */
    @TableField(value = "db_name")
    private String dbName;
    /**
     * 驱动
     */
    @TableField(value = "driver_class_name")
    private String driverClassName;
    /**
     * url
     */
    @TableField(value = "url")
    private String url;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 库名
     */
    @TableField(value = "database_name")
    private String databaseName;
    /**
     * 模式名
     */
    @TableField(value = "schema_name")
    private String schemaName;
    /**
     * 描述
     */
    @TableField(value = "`describe`")
    private String describe;
}
