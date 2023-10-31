package org.wheel.sqlparser.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.*;
import lombok.SneakyThrows;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.exception.SqlParserException;
import org.wheel.sqlparser.api.exception.enums.SqlParserExceptionEnum;
import org.wheel.sqlparser.api.pojo.DataSetViewBO;
import org.wheel.sqlparser.api.pojo.IDataSet;
import org.wheel.sqlparser.api.pojo.base.ISqlField;
import org.wheel.sqlparser.api.pojo.base.SqlFilterBO;

import java.util.List;

public class SqlSelectParserViewParser extends AbstractSqlSelectParser {

    DataSetViewBO dataSet;

    public SqlSelectParserViewParser(DbTypeEnum dbTypeEnum, IDataSet dataset) {
        super(dbTypeEnum, dataset);
        dataSet = (DataSetViewBO) dataset;
    }
    @SneakyThrows
    @Override
    public SQLSelect createSqlSelect()  {
        SQLSelectQueryBlock selectBody = (SQLSelectQueryBlock) this.dbTypeEnum.getSqlSelectQueryBlockClass().newInstance();
        SQLTableSource fromExpr = this.createFromExpr(this.dataSet.getTables(), this.dataSet.getSqlTableJoins());
        SQLExpr conditionExpr = this.createWhereExpr(this.dataSet.getFilter());
        SQLOrderBy sortExpr = this.createSortExpr(this.dataSet.getOrders());
        selectBody.setFrom(fromExpr);
        selectBody.setWhere(conditionExpr);
        selectBody.setOrderBy(sortExpr);
        return new SQLSelect(selectBody);
    }

    @Override
    public SQLSelect createSqlSelect(List<ISqlField> fields) {
        SQLSelect sqlSelect = this.createSqlSelect();
        return createSqlSelect(sqlSelect,fields);
    }

    @Override
    public SQLSelect createSqlSelect(SQLSelect sqlSelect, List<ISqlField> fields) {
        if(sqlSelect == null){
            throw new SqlParserException(SqlParserExceptionEnum.SQL_PARSER_TABLE_ERROR, "createSqlSelect sqlSelect对象为空");
        }
        List<SQLSelectItem> selectFieldExpr = this.createSelectFieldExpr(fields);
        SQLSelectGroupByClause groupExpr = this.createGroupExpr(fields);
        sqlSelect.getQueryBlock().setGroupBy(groupExpr);
        selectFieldExpr.forEach(expr -> {
            sqlSelect.getQueryBlock().addSelectItem(expr);
        });
        return sqlSelect;
    }



}
