package org.wheel.datasource.api;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import org.wheel.datasource.api.dto.DBConfigDTO;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.datasource.api.request.DBConfigRequest;

import java.sql.Connection;

public interface DBConfigApi {
    /**
     * 查询数据连接配置根据Code
     *
     * @return
     */
    default DBConfigDTO getConfigByCode(String dbCode) {
        DBConfigRequest request = new DBConfigRequest();
        request.setDbCode(dbCode);
        return getConfig(request);
    }

    /**
     * 查询数据连接配置根据ID
     *
     * @return
     */
    default DBConfigDTO getConfigById(Long dbId) {
        DBConfigRequest request = new DBConfigRequest();
        request.setDbId(dbId);
        return getConfig(request);
    }

    /**
     * 查询数据连接配置根据request入参
     *
     * @param request
     * @return
     */
    DBConfigDTO getConfig(DBConfigRequest request,Boolean isShowPassword);
    /**
     * 查询数据连接配置根据request入参
     *
     * @param request
     * @return
     */
    default DBConfigDTO getConfig(DBConfigRequest request){
        return getConfig(request,true);
    };


    /**
     * 测试连接
     *
     * @param request
     * @return
     */
    boolean getTestConnection(DBConfigRequest request);


    /**
     * 测试连接
     *
     * @param request
     * @return
     */
    ResponseData<Boolean> testConnection(DBConfigRequest request);

    /**
     * 获取连接 根据CODE
     *
     * @return
     */
    default Connection getConnectionByCode(String dbCode) {
        DBConfigRequest request = new DBConfigRequest();
        request.setDbCode(dbCode);
        return getConnection(request);
    }

    /**
     * 获取连接 根据CODE
     *
     * @return
     */
    default Connection getConnectionById(Long dbId) {
        DBConfigRequest request = new DBConfigRequest();
        request.setDbId(dbId);
        return getConnection(request);
    }

    /**
     * 获取连接
     *
     * @param request
     * @return
     */
    Connection getConnection(DBConfigRequest request);

    /**
     * 获取枚举
     * @param dbTypeCode
     * @return
     */
    DbTypeEnum getDbTypeEnum(String dbTypeCode);

}
