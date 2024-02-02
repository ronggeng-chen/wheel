package org.wheel.sqlparser.sdk.dataset;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.*;
import lombok.SneakyThrows;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.pojo.dataset.bo.DataSetViewBO;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;
import org.wheel.sqlparser.api.pojo.dataset.bo.ISqlField;
import org.wheel.sqlparser.api.pojo.dataset.bo.SqlFilterBO;

import java.util.List;

public class SqlSelectParserViewParser extends AbstractSqlSelectParser {

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
    public SQLSelect generateFieldSqlExpr(List<ISqlField> fields) {
        if (this.sqlSelect == null) {
            this.init();
        }
        List<SQLSelectItem> selectFieldExpr = this.createSelectFieldExpr(fields);
        SQLSelectGroupByClause groupExpr = this.createGroupExpr(fields);
        this.sqlSelect.getQueryBlock().setGroupBy(groupExpr);
        selectFieldExpr.forEach(expr -> {
            this.sqlSelect.getQueryBlock().addSelectItem(expr);
        });
        return this.sqlSelect;
    }


    @Override
    public SQLSelect generateFilterSqlExpr(SqlFilterBO filter) {
        if (this.sqlSelect == null) {
            this.init();
        }
        SQLExpr whereExpr = this.createWhereExpr(filter);
        this.sqlSelect.getQueryBlock().setWhere(whereExpr);
        return this.sqlSelect;
    }


    @Override
    public String toString() {
        if (this.sqlSelect == null) {
            this.init();
        }
        return this.sqlSelect.toString();
    }
}
