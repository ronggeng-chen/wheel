package org.wheel.datasource.api.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

@Data
public class DBMetaDataRequest extends BaseRequest {
    /**
     * 数据库ID
     */
    private Long dbId;
    /**
     * 数据库ID
     */
    private String dbCode;
    /**
     * 是否查询视图
     */
    private Boolean isQueryView;
    /**
     * 数据库
     */
    private String databaseName;
    /**
     * 模式名
     */
    private String schemaName;
    /**
     * 表逻辑名
     */
    private String tableCode;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 数据源
     */
    private DBConfigRequest dbConfigRequest;
}
