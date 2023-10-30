package org.wheel.datasource.api.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;

@Data
public class DBSqlRequest extends BaseRequest {
    /**
     * 数据库ID
     */
    private Long dbId;

    /**
     * 数据库ID
     */
    private String dbCode;

    /**
     * 脚本
     */
    private String sql;

    /**
     * 是否查询count语句
     */
    private boolean isCount;
}
