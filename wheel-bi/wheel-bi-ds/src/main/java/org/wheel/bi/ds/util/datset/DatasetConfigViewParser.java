package org.wheel.bi.ds.util.datset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNotExpr;
import com.alibaba.druid.sql.ast.statement.*;
import lombok.SneakyThrows;
import org.wheel.bi.api.enums.ds.DsSqlComparisonOperatorEnum;
import org.wheel.bi.api.enums.ds.DsSqlTernaryOperatorEnum;
import org.wheel.bi.api.exception.DsException;
import org.wheel.bi.api.exception.enums.DsExceptionEnum;
import org.wheel.bi.api.pojo.ds.dto.*;
import org.wheel.bi.ds.util.sqlparser.SqlSelectParserToolUtil;
import org.wheel.datasource.api.enums.DbTypeEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DatasetConfigViewParser extends AbstractDatasetConfigParser {

    public DatasetConfigViewParser(DbTypeEnum dbTypeEnum, DsDatasetDTO dataset) {
        super(dbTypeEnum, dataset);
    }

    @SneakyThrows
    @Override
    public void init() {
        this.sqlSelect = null;
        DsDatasetConfigViewDTO datsetConfigView = this.dataset.getDatsetConfigView();
        SQLSelectQueryBlock selectBody = (SQLSelectQueryBlock) this.dbTypeEnum.getSqlSelectQueryBlockClass().newInstance();
        SQLTableSource fromExpr = this.createFromExpr(datsetConfigView.getTables(), datsetConfigView.getJoins());
        SQLExpr conditionExpr = this.createWhereExpr(datsetConfigView.getFilter());
        SQLOrderBy sortExpr = this.createSortExpr(datsetConfigView.getOrders());
        selectBody.setFrom(fromExpr);
        selectBody.setWhere(conditionExpr);
        selectBody.setOrderBy(sortExpr);
        this.sqlSelect = new SQLSelect(selectBody);
    }

    @Override
    public SQLSelect getSQLSelect(List<DsDatasetFieldDTO> fields) {
        return null;
    }

    private SQLExpr createWhereExpr(DsDatasetConfigViewFilterDTO filter) {
        DsSqlTernaryOperatorEnum ternaryOperator = DsSqlTernaryOperatorEnum.getEnum(filter.getTernary());
        List<SQLExpr> conditionExprList = new ArrayList<>();
        if (CollUtil.isNotEmpty(filter.getConditions())) {
            //条件集合
            filter.getConditions().forEach(condition -> {
                SQLExpr fieldExpr = SQLUtils.toSQLExpr(condition.getLeftExpr());
                DsSqlComparisonOperatorEnum operatorEnum = DsSqlComparisonOperatorEnum.getEnum(condition.getOperator());
                SQLExpr conditionExpr = SqlSelectParserToolUtil.createCondition(fieldExpr, operatorEnum, condition.getValues());
                conditionExprList.add(conditionExpr);
            });
        }
        if (CollUtil.isNotEmpty(filter.getGroups())) {
            filter.getGroups().forEach(condition -> {
                SQLExpr sqlExpr = createWhereExpr(condition);
                conditionExprList.add(sqlExpr);
            });
        }
        return createConditionGroup(ternaryOperator, conditionExprList);
    }

    private SQLExpr createConditionGroup(DsSqlTernaryOperatorEnum ternaryOperatorEnum, List<SQLExpr> conditionExpr) {
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

//    public SQLSelectGroupByClause createGroupExpr(List<ISqlField> fields) {
//        SQLSelectGroupByClause groupBy = new SQLSelectGroupByClause();
//        fields.forEach(field -> {
//            if (StrUtil.equals(field.getFieldType(), FieldTypeEnum.DIMENSION.getCode())) {
//                SqlDimensionFieldBO dimensionField = (SqlDimensionFieldBO) field;
//                groupBy.addItem(SqlSelectParserToolUtil.createSqlPropertyExpr(dimensionField.getTableAlise(), dimensionField.getFieldCode()));
//            }
//        });
//        return groupBy;
//    }

    private SQLOrderBy createSortExpr(List<DsDatasetConfigViewOrderDTO> orders) {
        SQLOrderBy orderBy = new SQLOrderBy();
        if (CollUtil.isNotEmpty(orders)) {
            orders.stream().sorted(Comparator.comparing(DsDatasetConfigViewOrderDTO::getOrderNum)).forEach(order -> {
                orderBy.addItem(new SQLSelectOrderByItem(new SQLIdentifierExpr(order.getSqlExpr())));
            });
        }
        return orderBy;
    }


    private SQLTableSource createFromExpr(List<DsDatasetConfigViewTableDTO> tables, List<DsDatasetConfigViewJoinDTO> joins) {
        SQLTableSource fromExpr = null;
        DsDatasetConfigViewTableDTO mainTable = tables.stream().filter(DsDatasetConfigViewTableDTO::getMainFlag).findAny().orElse(null);
        if (mainTable == null) {
            throw new DsException(DsExceptionEnum.PARAM_ERROR, "缺少主表信息");
        }
        if (tables.size() == 1) {
            fromExpr = new SQLExprTableSource(new SQLIdentifierExpr(mainTable.getTableCode()), mainTable.getTableAlias());
            //tableMap.put(mainTable.getTableAlias(), mainTable);getTableAlise
        } else {
            //主表Expr
            SQLExprTableSource mainExpr = SqlSelectParserToolUtil.getTableExpr(mainTable.getTableCode(), mainTable.getTableAlias());
            //下一个join记录，默认是主表关联记录
            DsDatasetConfigViewJoinDTO nextJoin = joins.stream().filter(join -> mainTable.getTableAlias().equals(join.getPrimaryTableAlias())).findAny().orElse(null);
            if (nextJoin != null) {
                while (nextJoin != null) {
                    String foreignTableAlias = nextJoin.getForeignTableAlias();
                    //附表记录
                    DsDatasetConfigViewTableDTO foreignTable = tables.stream().filter(table -> StrUtil.equals(table.getTableCode(), foreignTableAlias)).findAny().orElse(null);
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
                    nextJoin = joins.stream().filter(join -> StrUtil.equals(foreignTable.getTableAlias(), join.getPrimaryTableAlias())).findAny().orElse(null);
                }
            } else {
                fromExpr = mainExpr;
            }
        }
        return fromExpr;
    }
}
