package org.wheel.datasource.api.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DBConfigRequest extends BaseRequest {

    private String keyword;

    /**
     * id
     */
    private Long dbId;
    /**
     * 数据源代码
     */
    private String dbCode;
    /**
     * 数据库类型 oracle sqlserver mysql 等等
     */
    private String dbType;
    /**
     * 数据源名称
     */
    private String dbName;
    /**
     * 驱动
     */
    private String driverClassName;
    /**
     * url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 模式名
     */
    private String schemaName;
    /**
     * 描述
     */
    private String describe;
}
