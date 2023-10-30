package org.wheel.datasource.api.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wheel.datasource.api.dto.DBDataSetDTO;
import org.wheel.datasource.api.dto.IDataSet;
import org.wheel.datasource.api.dto.IField;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBSqlSelectParser {

    DBConfigRequest request;

    String sql;

    DbType dbType;

    SQLSelect sqlSelect;

    public final static String ROW_NUM = "__row_number__";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DBSqlSelectParser(String sql, DbType dbType) {
        this.sql = sql;
        this.dbType = dbType;
        this.sqlSelect = getSqlSelect();
    }

    /**
     * 入口
     *
     * @param sql
     * @param dbType
     * @return
     */
    public static DBSqlSelectParser getDBSqlParserFactory(String sql, DbType dbType) {
        return new DBSqlSelectParser(sql, dbType);
    }

    /**
     * 获取SQLSelect对象
     *
     * @return
     */
    public SQLSelect getSqlSelect() {
        boolean checkSelect = checkSelectSql();
        if (!checkSelect) {
            throw new DBException(DBExceptionEnum.DB_ERROR, "sql is not select statement");
        }
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(sql, dbType);
        SQLSelectStatement stmt = (SQLSelectStatement) sqlStatementParser.parseStatement();
        return stmt.getSelect();
    }

    /**
     * 获取SQL类型
     * Token SELECT UPDATE
     *
     * @return
     */
    public Token getToken() {
        return getSQLStatementParser().getLexer().token();
    }

    public static void main(String[] args) {
        String sql = "select a from a select b from b select c from c";
        SQLStatementParser sqlStatementParser = new SQLStatementParser(sql,DbType.sqlserver);
        sqlStatementParser.parseStatement(true);
    }


    public SQLStatementParser getSQLStatementParser() {
        checkSql();
        return SQLParserUtils.createSQLStatementParser(sql, dbType);
    }

    public void checkSql() {
        try {
            SQLParserUtils.createSQLStatementParser(sql, dbType).parseStatement(true);
        } catch (Exception exception) {
            throw new DBException(DBExceptionEnum.DB_SQL_PARSER_ERROR, "sql不合法,{}", exception.getMessage());
        }
    }

    /**
     * 是否是select语句
     *
     * @return
     */
    public boolean checkSelectSql() {
        if (getToken().name.equals(Token.SELECT.name)) {
            return true;
        }
        return false;
    }

    /**
     * 格式化SQL
     *
     * @return
     */
    public static String formatSql(String sql, DbType dbType) {
        return SQLUtils.format(sql, dbType);
    }


    private String getSelectItemName(SQLSelectItem item) {
        return StrUtil.isNotEmpty(item.getAlias()) ? item.getAlias() : item.getExpr().toString();
    }

    /**
     * 获取字段通过SQL
     *
     * @return
     */
    public List<String> getFieldToSql() {
        List<SQLSelectItem> selectItemList = this.sqlSelect.getQueryBlock().getSelectList();
        List<String> list = new ArrayList<>();
        selectItemList.forEach(item -> {
            list.add(getSelectItemName(item));
        });
        return list;
    }

    public boolean isUnion() {
        if (this.sqlSelect.getQuery() instanceof SQLSelectQueryBlock) {
            return false;
        } else if (this.sqlSelect.getQuery() instanceof SQLUnionQuery) {
            return true;
        }
        return false;
    }


    /**
     * 获取分页
     * TODO: PagerUtils 处理分页语法树 SQL Server 不支持 Limit语法offset项 ，只有TOP语法
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public String getSqlPage(Integer pageNo, Integer pageSize) {
        if (pageNo == null && pageSize == null) {
            return this.sql;
        }
        String pageSql = "";
        SQLSelectQueryBlock sqlSelectQueryBlock = SQLParserUtils.createSelectQueryBlock(this.dbType);
        sqlSelectQueryBlock.getSelectList().add(SQLUtils.toSelectItem("*", DbType.sqlserver));
        sqlSelectQueryBlock.setFrom(this.sqlSelect, "T");
        if (this.sqlSelect.getDbType().name().equals(DbType.sqlserver.name())) {
            pageSql = "WITH selectTemp AS ( SELECT *, ROW_NUMBER ( ) OVER ( ORDER BY CURRENT_TIMESTAMP ) AS __row_number__ FROM ({}) T1 ) SELECT\n" + "* \n" + "FROM\n" + "\tselectTemp \n" + "WHERE\n" + "\t__row_number__ BETWEEN {} \n" + "\tAND {} \n" + "ORDER BY\n" + "\t__row_number__";
            pageSql = StrUtil.format(pageSql, sqlSelectQueryBlock.toString(), pageNo, pageSize);
        } else {
            sqlSelectQueryBlock.limit(pageSize, pageNo);
            pageSql = sqlSelectQueryBlock.toString();
        }
        return pageSql;
    }

    /**
     * 获取总条数
     * TODO: PagerUtils COUNT
     *
     * @return
     */
    public String getSqlCount() {
        return PagerUtils.count(sql, this.dbType);
    }

    /**
     * 获取字段通过结果集
     *
     * @return
     */
    public List<String> getFieldToResult(DBConfigRequest request) throws DBException {
        Connection connection = DBConnectionUtil.getConnection(request);
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> fieldList = new ArrayList<>();
        String sql = getSqlPage(1, 1);
        logger.info("sql : {}", sql);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = resultSetMetaData.getColumnName(i + 1);
                if (!StrUtil.equals(ROW_NUM, columnName)) {
                    fieldList.add(columnName);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_EXECUTE_ERROR, "sql parser to field error");
        } finally {
            DbUtil.close(connection, statement, resultSet);
        }
        return fieldList;
    }

    public IDataSet execute(DBConfigRequest request, boolean isCount, Integer pageNo, Integer pageSize) throws DBException {
        Connection connection = DBConnectionUtil.getConnection(request);
        Statement statement = null;
        ResultSet resultSet = null;
        IDataSet dataset;
        String executeSql = getSqlPage(pageNo, pageSize);
        try {
            statement = connection.createStatement();
            logger.info("run sql : {}", executeSql);
            resultSet = statement.executeQuery(executeSql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<String> fieldList = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                String columnName = resultSetMetaData.getColumnName(i + 1);
                if (!StrUtil.equals(ROW_NUM, columnName)) {
                    fieldList.add(columnName);
                }
            }
            dataset = new DBDataSetDTO(fieldList);
            while (resultSet.next()) {
                for (IField<Object> field : dataset.getFields()) {
                    Object object = resultSet.getObject(field.getFieldCode());
                    field.addValue(object);
                }
            }
            if (isCount) {
                if (pageNo != null && pageSize != null) {
                    String countSql = getSqlCount();
                    logger.info("run count sql : {}", countSql);
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(countSql);
                    while (resultSet.next()) {
                        dataset.setTotalCount(resultSet.getInt(1));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            DBException dbException = new DBException(DBExceptionEnum.DB_EXECUTE_ERROR);
            dbException.setStackTrace(ex.getStackTrace());
            throw dbException;
        } finally {
            DbUtil.close(connection, statement, resultSet);
        }

        return dataset;
    }

}
