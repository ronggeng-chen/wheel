package org.wheel.datasource.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.DbType;
import org.springframework.stereotype.Service;
import org.wheel.datasource.api.DBConfigApi;
import org.wheel.datasource.api.dto.DBConfigDTO;
import org.wheel.datasource.api.dto.IDataSet;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;
import org.wheel.datasource.api.request.DBSqlRequest;
import org.wheel.datasource.api.util.DBSqlSelectParser;
import org.wheel.datasource.business.service.DBSqlParserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DBSqlParserServiceImpl implements DBSqlParserService {
    @Resource
    DBConfigApi dbConfigApi;
    //######################################API########################################

    @Override
    public DBSqlSelectParser getDBSqlSelectParser(DBSqlRequest request) {
        DBConfigRequest configRequest = getConfigRequest(request);
        return DBSqlSelectParser.getDBSqlParserFactory(request.getSql(), getDbType(configRequest.getDbType()));
    }

    @Override
    public DbType getDbType(String dbTypeCode) {
        return dbConfigApi.getDbTypeEnum(dbTypeCode).getDbType();
    }

    @Override
    public IDataSet execute(DBSqlRequest request) throws DBException {
        DBConfigRequest configRequest = getConfigRequest(request);
        DBSqlSelectParser dbSqlSelectParser = DBSqlSelectParser.getDBSqlParserFactory(request.getSql(), getDbType(configRequest.getDbType()));
        return dbSqlSelectParser.execute(configRequest,request.isCount(), request.getPageNo(), request.getPageSize());
    }

    private DBConfigRequest getConfigRequest(DBSqlRequest request) {
        DBConfigRequest configRequest = new DBConfigRequest();
        configRequest.setDbId(request.getDbId());
        configRequest.setDbCode(request.getDbCode());
        DBConfigDTO dbConfig = dbConfigApi.getConfig(configRequest);
        if (ObjectUtil.isEmpty(dbConfig)) {
            throw new DBException(DBExceptionEnum.DB_ERROR, "找不到数据源配置");
        }
        return BeanUtil.copyProperties(dbConfig, DBConfigRequest.class);
    }


    @Override
    public String getFormatSql(DBSqlRequest request) {
        DBConfigRequest configRequest = getConfigRequest(request);
        return DBSqlSelectParser.formatSql(request.getSql(), getDbType(configRequest.getDbType()));
    }

    @Override
    public List<String> getFieldToSql(DBSqlRequest request) {
        return getDBSqlSelectParser(request).getFieldToSql();
    }

    @Override
    public List<String> getFieldToResult(DBSqlRequest request) throws DBException {
        DBConfigRequest configRequest = getConfigRequest(request);
        DBSqlSelectParser dbSqlSelectParser = DBSqlSelectParser.getDBSqlParserFactory(request.getSql(), getDbType(configRequest.getDbType()));
        return dbSqlSelectParser.getFieldToResult(configRequest);
    }

    @Override
    public boolean isUnionSql(DBSqlRequest request) {
        return getDBSqlSelectParser(request).isUnion();
    }

    @Override
    public boolean checkSelectSql(DBSqlRequest request) {
        return getDBSqlSelectParser(request).checkSelectSql();
    }

    //######################################API########################################

}
