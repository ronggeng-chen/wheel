package org.wheel.datasource.api;


import com.alibaba.druid.DbType;
import org.wheel.datasource.api.dto.IDataSet;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBSqlRequest;
import org.wheel.datasource.api.util.DBSqlSelectParser;

import java.util.List;

/**
 * 只处理查询类SQL
 */
public interface DBSqlSelectParserApi {

    DBSqlSelectParser getDBSqlSelectParser(DBSqlRequest request);

    /**
     * 获取DbType
     *
     * @param dbTypeCode
     * @return
     */
    DbType getDbType(String dbTypeCode);

    /**
     * 执行SQL
     *
     * @param dbId
     * @param sql
     * @return
     */
    default IDataSet getResultById(Long dbId, String sql) {
        return getResultPageById(dbId, sql, null, null);
    }

    /**
     * 执行SQL
     *
     * @param dbCode
     * @param sql
     * @return
     */
    default IDataSet getResultByCode(String dbCode, String sql) {
        return getResultPageByCode(dbCode, sql, null, null);
    }

    /**
     * 执行SQL
     *
     * @param dbId
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     */
    default IDataSet getResultPageById(Long dbId, String sql, Integer pageNo, Integer pageSize) {
        DBSqlRequest request = new DBSqlRequest();
        request.setDbId(dbId);
        request.setSql(sql);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return execute(request);
    }

    /**
     * 执行SQL
     *
     * @param dbCode
     * @param sql
     * @return
     */
    default IDataSet getResultPageByCode(String dbCode, String sql, Integer pageNo, Integer pageSize)  {
        DBSqlRequest request = new DBSqlRequest();
        request.setDbCode(dbCode);
        request.setSql(sql);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        return execute(request);
    }

    /**
     * 执行SQL
     *
     * @param request{ dbId,dbCode,sql,pageNo,pageSize}
     * @return
     */
    IDataSet execute(DBSqlRequest request);

    /**
     * 格式化SQL
     *
     * @return
     */
    String getFormatSql(DBSqlRequest request);


    /**
     * 获取SQL解析的字段项
     *
     * @return
     */
    List<String> getFieldToSql(DBSqlRequest request);

    /**
     * 获取SQL执行结果的字段项
     *
     * @return
     */
    List<String> getFieldToResult(DBSqlRequest request) throws DBException;

    /**
     * 是否是UnionSql
     *
     * @param request
     * @return
     */
    boolean isUnionSql(DBSqlRequest request);

    /**
     * 是否是select语句
     *
     * @param request
     * @return
     */
    boolean checkSelectSql(DBSqlRequest request);


}
