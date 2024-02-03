package org.wheel.sqlparser.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import org.wheel.sqlparser.api.enums.TernaryOperatorEnum;
import org.wheel.sqlparser.api.pojo.base.bo.*;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:48
 */
public interface IDataSetSqlParser {
    
    List<SQLSelectItem> createSelectFieldExpr(List<ISqlField> baseFieldList);

    SQLTableSource createFromExpr(List<SqlTableBO> sqlTableList, List<SqlTableJoinBO> sqlTableJoinList);

    SQLExpr createWhereExpr(SqlFilterBO sqlFilter);

    SQLExpr createConditionGroup(TernaryOperatorEnum ternaryOperatorEnum, List<SQLExpr> conditionExpr);

    SQLOrderBy createSortExpr(List<SqlOrderBO> sqlSortList);

    void init();

    void checkValid();

    SQLSelect generateSqlExpr(List<ISqlField> fields);

    SQLSelect generateSqlExpr(List<ISqlField> fields, SqlFilterBO filter);
}
