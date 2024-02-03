package org.wheel.sqlparser.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNotExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import lombok.extern.slf4j.Slf4j;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.enums.ComparisonOperatorEnum;
import org.wheel.sqlparser.api.enums.FieldTypeEnum;
import org.wheel.sqlparser.api.enums.TernaryOperatorEnum;
import org.wheel.sqlparser.api.exception.SqlParserException;
import org.wheel.sqlparser.api.exception.enums.SqlParserExceptionEnum;
import org.wheel.sqlparser.api.pojo.base.bo.*;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;
import org.wheel.sqlparser.api.util.SqlSelectParserToolUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractDataSetSqlParser implements IDataSetSqlParser {
    protected DbTypeEnum dbTypeEnum;

    protected SQLSelect sqlSelect;

    public AbstractDataSetSqlParser(DbTypeEnum dbTypeEnum, IDataSet dataSet) {
        this.dbTypeEnum = dbTypeEnum;
    }

    @Override
    public List<SQLSelectItem> createSelectFieldExpr(List<ISqlField> baseFieldList) {
        List<SQLSelectItem> sqlSelectItems = new ArrayList<>();
        for (ISqlField field : baseFieldList) {
            SQLSelectItem selectItemExpr = SqlSelectParserToolUtil.createSelectItemExpr(field.getSqlSnippet(), field.getKey());
            sqlSelectItems.add(selectItemExpr);
        }
        return sqlSelectItems;
    }

    @Override
    public SQLExpr createWhereExpr(SqlFilterBO sqlFilterBO) {
        if (StrUtil.equals(sqlFilterBO.getTernary(), YesOrNotEnum.Y.getCode())) {
            TernaryOperatorEnum ternaryOperator = TernaryOperatorEnum.getEnum(sqlFilterBO.getTernary());
            List<SQLExpr> conditionExprList = new ArrayList<>();
            sqlFilterBO.getChildren().forEach(condition -> {
                SQLExpr sqlExpr = createWhereExpr(condition);
                conditionExprList.add(sqlExpr);
            });
            return createConditionGroup(ternaryOperator, conditionExprList);
        } else {
            SQLExpr fieldExpr = SQLUtils.toSQLExpr(sqlFilterBO.getCondition().getSqlSnippet());
            ComparisonOperatorEnum operatorEnum = ComparisonOperatorEnum.getEnum(sqlFilterBO.getCondition().getOperator());
            SQLExpr condition = SqlSelectParserToolUtil.createCondition(fieldExpr, operatorEnum, sqlFilterBO.getCondition().getValues());
            return condition;
        }
    }

    @Override
    public SQLExpr createConditionGroup(TernaryOperatorEnum ternaryOperatorEnum, List<SQLExpr> conditionExpr) {
        if (ArrayUtil.isEmpty(conditionExpr)) {
            throw new IllegalArgumentException("At least one condition is required.");
        }
        SQLBinaryOpExpr conditionGroup = null;
        for (SQLExpr condition : conditionExpr) {
            if (conditionGroup == null) {
                conditionGroup = new SQLBinaryOpExpr(condition, ternaryOperatorEnum.getToken(), null);
            } else {
                conditionGroup = new SQLBinaryOpExpr(conditionGroup, ternaryOperatorEnum.getToken(), condition);
            }
        }
        if (ternaryOperatorEnum == ternaryOperatorEnum.NOT) {
            return new SQLNotExpr(conditionGroup);
        }
        return conditionGroup;
    }

    public SQLSelectGroupByClause createGroupExpr(List<ISqlField> fields) {
        SQLSelectGroupByClause groupBy = new SQLSelectGroupByClause();
        fields.forEach(field -> {
            if (StrUtil.equals(field.getFieldType(), FieldTypeEnum.DIMENSION.getCode())) {
                SqlDimensionFieldBO dimensionField = (SqlDimensionFieldBO) field;
                groupBy.addItem(SqlSelectParserToolUtil.createSqlPropertyExpr(dimensionField.getTableAlise(), dimensionField.getFieldCode()));
            }
        });
        return groupBy;
    }

    @Override
    public SQLOrderBy createSortExpr(List<SqlOrderBO> sqlSortList) {
        SQLOrderBy orderBy = new SQLOrderBy();
        if (CollUtil.isNotEmpty(sqlSortList)) {
            sqlSortList.forEach(sort -> {
                orderBy.addItem(new SQLSelectOrderByItem(new SQLIdentifierExpr(sort.getFieldKey())));
            });
        }
        return orderBy;
    }


    @Override
    public SQLTableSource createFromExpr(List<SqlTableBO> sqlTableList, List<SqlTableJoinBO> sqlTableJoinList) {
        SQLTableSource fromExpr = null;
        SqlTableBO mainTable = sqlTableList.stream().filter(SqlTableBO::getIsMain).findAny().orElse(null);
        if (mainTable == null) {
            throw new SqlParserException(SqlParserExceptionEnum.SQL_PARSER_TABLE_ERROR, "缺少主表信息");
        }
        if (sqlTableList.size() == 1) {
            fromExpr = new SQLExprTableSource(new SQLIdentifierExpr(mainTable.getTableCode()), mainTable.getTableAlias());
            //tableMap.put(mainTable.getTableAlias(), mainTable);getTableAlise
        } else {
            //主表Expr
            SQLExprTableSource mainExpr = SqlSelectParserToolUtil.getTableExpr(mainTable.getTableCode(), mainTable.getTableAlias());
            //下一个join记录，默认是主表关联记录
            SqlTableJoinBO nextJoin = sqlTableJoinList.stream().filter(join -> mainTable.getTableAlias().equals(join.getPrimaryTableAlias())).findAny().orElse(null);
            if (nextJoin != null) {
                while (nextJoin != null) {
                    String foreignTableAlias = nextJoin.getForeignTableAlias();
                    //附表记录
                    SqlTableBO foreignTable = sqlTableList.stream().filter(table -> StrUtil.equals(table.getTableCode(), foreignTableAlias)).findAny().orElse(null);
                    if (foreignTable == null) {
                        break;
                    }
                    //附表Expr
                    SQLExprTableSource foreignExpr = SqlSelectParserToolUtil.getTableExpr(foreignTable.getTableCode(), foreignTable.getTableAlias());
                    //关联条件Expr
                    SQLBinaryOpExpr joinExpr = SqlSelectParserToolUtil.createJoinExpr(nextJoin.getPrimaryTableAlias(), nextJoin.getPrimaryFieldCode(), nextJoin.getForeignTableAlias(), nextJoin.getForeignFieldCode());
                    //创建表
                    if (fromExpr == null) {
                        fromExpr = new SQLJoinTableSource(mainExpr, SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN, foreignExpr, joinExpr);
                    } else {
                        fromExpr = new SQLJoinTableSource(fromExpr, SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN, foreignExpr, joinExpr);
                    }
                    nextJoin = sqlTableJoinList.stream().filter(join -> StrUtil.equals(foreignTable.getTableAlias(), join.getPrimaryTableAlias())).findAny().orElse(null);
                }
            } else {
                fromExpr = mainExpr;
            }
        }
        return fromExpr;
    }

    @Override
    public void checkValid() {
        // 创建 WallFilter
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true); // 允许多条语句
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(config);
        if (!wallFilter.checkValid(this.sqlSelect.toString())) {
            throw new SqlParserException(SqlParserExceptionEnum.SQL_PARSER_PARAM_ERROR);
        }
    }


    @Override
    public String toString() {
        if (this.sqlSelect == null) {
            this.init();
        }
        return this.sqlSelect.toString();
    }
}