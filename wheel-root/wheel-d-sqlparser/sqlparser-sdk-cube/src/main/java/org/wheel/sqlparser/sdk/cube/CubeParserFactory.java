package org.wheel.sqlparser.sdk.cube;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.enums.ComparisonOperatorEnum;
import org.wheel.sqlparser.api.enums.StatisticalMethodEnum;
import org.wheel.sqlparser.api.enums.TernaryOperatorEnum;
import org.wheel.sqlparser.api.pojo.cube.request.CubeRequest;
import org.wheel.sqlparser.api.pojo.dataset.bo.*;
import org.wheel.sqlparser.sdk.dataset.ISqlSelectParser;
import org.wheel.sqlparser.sdk.dataset.SqlSelectParserFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:02
 */
public class CubeParserFactory {

    CubeRequest request;

    DbTypeEnum dbTypeEnum;

    Map<String, CubeHandleModel> cubeHandleModelMap;

    Map<String, IDataSet> dataSetMap;

    private CubeParserFactory() {
    }

    private CubeParserFactory(CubeRequest request, Map<String, IDataSet> dataSetMap) {
        this.request = request;
        this.dataSetMap = dataSetMap;
        this.cubeHandleModelMap = new HashMap<>();
        this.request.getFields().forEach(field -> {
            IDataSet iDataSet = this.dataSetMap.get(field.getDataSetCode());
            if (iDataSet == null) {
                return;
            }
            ISqlField iDataSetField = iDataSet.getFieldMap().get(field.getFieldKey());
            if (iDataSetField == null) {
                return;
            }
            CubeHandleModel cubeHandleModel = new CubeHandleModel();
            StatisticalMethodEnum statisticalMethodEnum = StatisticalMethodEnum.getEnum(field.getStatisticalMethod());
            if (statisticalMethodEnum == StatisticalMethodEnum.TQ) {
                cubeHandleModel.getSelectTQFields().add(iDataSetField);
            } else if (statisticalMethodEnum == StatisticalMethodEnum.HQ) {
                cubeHandleModel.getSelectHQFields().add(iDataSetField);
            } else {
                cubeHandleModel.getSelectFields().add(iDataSetField);
            }
            this.cubeHandleModelMap.put(iDataSet.getKey(), cubeHandleModel);
        });
    }

    public static CubeParserFactory getInstance(CubeRequest request, Map<String, IDataSet> dataSetMap) {
        return new CubeParserFactory(request, dataSetMap);
    }


    public SQLSelect createSqlExpr(CubeHandleModel cubeHandleModel) {
        IDataSet iDataSet = this.dataSetMap.get(cubeHandleModel.getDataSetCode());
        ISqlSelectParser sqlParser = SqlSelectParserFactory.getSqlParser(dbTypeEnum, iDataSet);
        SqlFilterBO sqlWhereExpr = createSqlWhereExpr(iDataSet.getDataSetCode());
        SQLSelect SqlSelectByInfo = sqlParser.generateSqlExpr(cubeHandleModel.getSelectFields(), sqlWhereExpr);

        SQLSelect SqlSelectByTQ = sqlParser.generateSqlExpr(cubeHandleModel.getSelectTQFields(), sqlWhereExpr);

        SQLSelect SqlSelectByHQ = sqlParser.generateSqlExpr(cubeHandleModel.getSelectHQFields(), sqlWhereExpr);

        return SqlSelectByInfo;
    }


    public SqlFilterBO createSqlWhereExpr(String dataSetCode) {
        IDataSet iDataSet = this.dataSetMap.get(dataSetCode);
        SqlFilterBO sqlFilterBO = new SqlFilterBO();
        sqlFilterBO.setTernary(TernaryOperatorEnum.AND.getCode());
        sqlFilterBO.setChildren(new ArrayList<>());
        if (this.request.getStartTime() != null && this.request.getEndTime() != null) {
            ISqlField iSqlField = iDataSet.getFieldMap().get(iDataSet.getTimeFieldKey());
            SqlComputeConditionBO dateRangeCondition = new SqlComputeConditionBO()
                    .setLeftSnippet(iSqlField.getSqlSnippet())
                    .setOperator(ComparisonOperatorEnum.BETWEEN.getCode())
                    .setValues(ListUtil.of(this.request.getStartTime(), this.request.getEndTime()));
            sqlFilterBO.getChildren().add(new SqlFilterBO().setCondition(dateRangeCondition));
        }
        return sqlFilterBO;
    }


    public SQLSelect createSqlExpr() {
return null;
    }


    public String parserCurrentTimeSqlExpr() {
//        List<SqlFieldBO> infoSqlField = new ArrayList<>();
//        List<SqlFieldBO> tqSqlField = new ArrayList<>();
//        List<SqlFieldBO> hqSqlField = new ArrayList<>();
//        request.getFields().forEach(field -> {
//            StatisticalMethodEnum statisticalMethodEnum = StatisticalMethodEnum.getEnum(field.getStatisticalMethod());
//            if (statisticalMethodEnum == StatisticalMethodEnum.TB || statisticalMethodEnum == StatisticalMethodEnum.TQ) {
//                tqSqlField.add(field);
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.HB || statisticalMethodEnum == StatisticalMethodEnum.HQ) {
//                hqSqlField.add(field);
//            } else {
//                infoSqlField.add(field);
//            }
//        });
//        //时间控件
//        SqlFilterControlBO dateControl = request.getConditions().stream().filter(condition -> StrUtil.equals(condition.getControlType(), SqlControlTypeEnum.DATE.getCode())).findAny().orElse(null);
//        Map<String, SQLSelect> withSqlSelectItemMap = new HashMap<>();
//        //本期SQL
//        SQLSelect infoSqlSelect = sqlSelectParserFactory.getSqlSelect(infoSqlField);
//        withSqlSelectItemMap.put("info",infoSqlSelect);
//        //同期SQL
//        SQLSelect tqSqlSelect = sqlSelectParserFactory.getSqlSelect(tqSqlField);
//        withSqlSelectItemMap.put("tq",tqSqlSelect);
//        //环期SQL
//        SQLSelect hqSqlSelect = sqlSelectParserFactory.getSqlSelect(hqSqlField);
//        withSqlSelectItemMap.put("hq",hqSqlSelect);
//        //查询结果SQL
//        SQLSelect resultSqlSelect = new SQLSelect();
//        //SqlSelectParserUtil.createFromExpr()
//
//        //完整SQL
//        SQLSelect withSqlSelect = SqlSelectParserToolFactory.createWithSqlSelect(resultSqlSelect, withSqlSelectItemMap);
        return null;
    }
}
