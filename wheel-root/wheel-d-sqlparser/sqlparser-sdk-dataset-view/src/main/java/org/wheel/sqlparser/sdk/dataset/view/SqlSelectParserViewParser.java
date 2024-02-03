package org.wheel.sqlparser.sdk.dataset.view;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.*;
import lombok.SneakyThrows;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.AbstractDataSetSqlParser;
import org.wheel.sqlparser.api.pojo.base.bo.ISqlField;
import org.wheel.sqlparser.api.pojo.base.bo.SqlFilterBO;
import org.wheel.sqlparser.api.pojo.dataset.bo.DataSetViewBO;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;

import java.util.List;

public class SqlSelectParserViewParser extends AbstractDataSetSqlParser {

    DataSetViewBO dataSet;

    public SqlSelectParserViewParser(DbTypeEnum dbTypeEnum, IDataSet dataset) {
        super(dbTypeEnum, dataset);
        dataSet = (DataSetViewBO) dataset;
    }

    @SneakyThrows
    @Override
    public void init() {
        this.sqlSelect = null;
        SQLSelectQueryBlock selectBody = (SQLSelectQueryBlock) this.dbTypeEnum.getSqlSelectQueryBlockClass().newInstance();
        SQLTableSource fromExpr = this.createFromExpr(this.dataSet.getTables(), this.dataSet.getSqlTableJoins());
        SQLExpr conditionExpr = this.createWhereExpr(this.dataSet.getFilter());
        SQLOrderBy sortExpr = this.createSortExpr(this.dataSet.getOrders());
        selectBody.setFrom(fromExpr);
        selectBody.setWhere(conditionExpr);
        selectBody.setOrderBy(sortExpr);
        this.sqlSelect = new SQLSelect(selectBody);
    }

    @Override
    public SQLSelect generateSqlExpr(List<ISqlField> fields) {
        if (this.sqlSelect == null) {
            this.init();
        }
        SQLSelect clone = this.sqlSelect.clone();
        List<SQLSelectItem> selectFieldExpr = this.createSelectFieldExpr(fields);
        SQLSelectGroupByClause groupExpr = this.createGroupExpr(fields);
        clone.getQueryBlock().setGroupBy(groupExpr);
        selectFieldExpr.forEach(expr -> {
            this.sqlSelect.getQueryBlock().addSelectItem(expr);
        });
        return clone;
    }

    @Override
    public SQLSelect generateSqlExpr(List<ISqlField> fields, SqlFilterBO filter) {
        SQLSelect clone = generateSqlExpr(fields);
        if (filter != null) {
            SQLExpr whereExpr = this.createWhereExpr(filter);
            clone.getQueryBlock().setWhere(whereExpr);
        }
        return clone;
    }
}
