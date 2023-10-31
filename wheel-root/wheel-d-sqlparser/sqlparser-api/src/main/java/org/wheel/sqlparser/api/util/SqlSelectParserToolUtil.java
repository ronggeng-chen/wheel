package org.wheel.sqlparser.api.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.alibaba.druid.sql.parser.Token;
import org.wheel.sqlparser.api.enums.ComparisonOperatorEnum;
import org.wheel.sqlparser.api.enums.TernaryOperatorEnum;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-08-22-10:50
 * @Modified By:
 */
public class SqlSelectParserToolUtil {
    public static SQLExprTableSource getTableExpr(String tableCode, String alias) {
        return new SQLExprTableSource(new SQLIdentifierExpr(tableCode), alias);
    }

    public static SQLBinaryOpExpr createJoinExpr(String primaryTableAlias, String primaryFieldCode, String foreignTableAlias, String foreignFieldCode) {
        return new SQLBinaryOpExpr(
                new SQLIdentifierExpr(primaryTableAlias + "." + primaryFieldCode),
                SQLBinaryOperator.Equality,
                new SQLIdentifierExpr(foreignTableAlias + "." + foreignFieldCode));
    }

    public static SQLSelectItem createSelectItemExpr(String expression, String alias) {
        SQLSelectItem item = new SQLSelectItem();
        SQLExpr expr = SQLUtils.toSQLExpr(expression);
        item.setAlias(alias);
        item.setExpr(expr);
        return item;
    }

    public static SQLSelectItem createSelectItemExpr(String tableAlias, String fieldCode, String alias) {
        SQLSelectItem item = new SQLSelectItem();
        item.setAlias(alias);
        item.setExpr(createSqlPropertyExpr(tableAlias, fieldCode));
        return item;
    }

    public static SQLPropertyExpr createSqlPropertyExpr(String tableAlias, String fieldCode) {
        SQLPropertyExpr sqlExpr = new SQLPropertyExpr();
        sqlExpr.setOwner(tableAlias);
        sqlExpr.setName(fieldCode);
        return sqlExpr;
    }
    public static SQLExpr createCondition(SQLExpr columnExpr, ComparisonOperatorEnum operator, List<Object> values) {
        SQLExpr valueExpr = SQLExprUtils.fromJavaObject(values.get(0), TimeZone.getDefault());
        SQLExpr sqlExpr = null;
        switch (operator) {
            case IN:
            case NOT_IN:
                SQLInListExpr in = new SQLInListExpr(columnExpr, operator == ComparisonOperatorEnum.IN);
                values.forEach(val -> {
                    in.addTarget(SQLExprUtils.fromJavaObject(val, TimeZone.getDefault()));
                });
                sqlExpr = in;
                break;
            case NOT_NULL:
            case IS_NULL:
                sqlExpr = new SQLBinaryOpExpr(columnExpr, operator.getToken(), new SQLIdentifierExpr(Token.NULL.name));
                break;
            case GT:
            case GTE:
            case LT:
            case LTE:
            case EQ:
            case NOT_EQ:
                sqlExpr = new SQLBinaryOpExpr(columnExpr, operator.getToken(), valueExpr);
                break;
            case BETWEEN:
                sqlExpr = new SQLBetweenExpr(columnExpr, valueExpr, SQLExprUtils.fromJavaObject(values.get(1), TimeZone.getDefault()));
                break;
            case LIKE:
                sqlExpr = new SQLBinaryOpExpr(columnExpr, operator.getToken(), new SQLCharExpr(StrUtil.concat(false, Token.PERCENT.name, values.get(0).toString(), Token.PERCENT.name)));
                break;
        }
        return sqlExpr;
    }


    public static SQLExpr createConditionGroup(TernaryOperatorEnum ternaryOperatorEnum, List<SQLExpr> conditionExpr) {
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

    public static SQLSelect createWithSqlSelect(SQLSelect resultSqlSelect, Map<String,SQLSelect> withItemSqlSelectMap){
        SQLWithSubqueryClause withClause = new SQLWithSubqueryClause();
        withItemSqlSelectMap.forEach((key,sqlSelectItem)->{
            withClause.addEntry(new SQLWithSubqueryClause.Entry(key, sqlSelectItem));
        });
        resultSqlSelect.setWithSubQuery(withClause);
        return resultSqlSelect;
    }

    public static SQLLimit createLimit(Integer pageNo,Integer pageSize){
        SQLIntegerExpr limit = new SQLIntegerExpr(pageSize);
        SQLIntegerExpr offset = new SQLIntegerExpr((pageNo - 1) * pageSize);
        return new SQLLimit(limit, offset);
    }
    public static void main(String[] args) {
    }
}
