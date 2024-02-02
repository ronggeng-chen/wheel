package org.wheel.sqlparser.sdk.cube;


import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.enums.StatisticalMethodEnum;
import org.wheel.sqlparser.api.pojo.cube.bo.CubeDomainExprBO;
import org.wheel.sqlparser.api.pojo.cube.bo.CubeDomainFieldExprBO;
import org.wheel.sqlparser.api.pojo.cube.bo.CubeResultFieldExprBO;
import org.wheel.sqlparser.api.pojo.cube.request.CubeFieldRequest;
import org.wheel.sqlparser.api.pojo.cube.request.CubeRequest;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;
import org.wheel.sqlparser.api.pojo.dataset.bo.ISqlField;
import org.wheel.sqlparser.sdk.dataset.ISqlSelectParser;
import org.wheel.sqlparser.sdk.dataset.SqlSelectParserFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:02
 */
public class CubeParserFactory {

    CubeRequest request;

    DbTypeEnum dbTypeEnum;

    Map<String, IDataSet> dataSetMap;

    Map<String, CubeDomainExprBO> cubeDomainExprMap;

    List<CubeResultFieldExprBO> cubeResultFieldExprList;

    private CubeParserFactory() {
    }

    private CubeParserFactory(CubeRequest request, Map<String, IDataSet> dataSetMap) {
        this.request = request;
        this.dataSetMap = dataSetMap;
        this.cubeHandleModelMap = new HashMap<>();

        String alias;

        String dataSetCode;

        String fieldKey;

        String statisticalMethod;

        /**
         *(1)  T1_FIELD_A T1 SUM
         *(1)  T2_FIELD_A T2 SUM
         *(1)  T2_FIELD_A T2 TB
         *(1)  T2_FIELD_A T2 HB
         */

        /**
         * 按数据集分组
         *      T1
         *          T1_FIELD_A SUM
         *      T2
         *          T2_FIELD_A SUM
         *          T2_FIELD_A TB
         *              T2_FIELD_A TQ
         *              (info-tq)/tq TB
         *          T2_FIELD_A HB
         */
        Map<String, List<CubeFieldRequest>> domainFieldMap = this.request.getFields().stream().filter(field -> StrUtil.isNotBlank(field.getDataSetCode())).collect(Collectors.groupingBy(CubeFieldRequest::getDataSetCode));
        domainFieldMap.forEach((key, value) -> {
            IDataSet iDataSet = this.dataSetMap.get(key);
            if (iDataSet == null) {
                return;
            }
            cubeDomainExprMap.put(key, new CubeDomainExprBO(key, new ArrayList<>(), this.request.getStartTime(), this.request.getEndTime()));
            value.forEach(field -> {
                ISqlField iDataSetField = iDataSet.getFieldMap().get(field.getFieldKey());
                if (iDataSetField == null) {
                    return;
                }
                StatisticalMethodEnum statisticalMethodEnum = StatisticalMethodEnum.getEnum(field.getStatisticalMethod());
                CubeDomainExprBO cubeDomainExprBO = cubeDomainExprMap.get(key);
                cubeDomainExprBO.getFieldList().add(new CubeDomainFieldExprBO(field.getAlias(), iDataSetField.getSqlSnippet()));


                if (statisticalMethodEnum == StatisticalMethodEnum.TQ || statisticalMethodEnum == StatisticalMethodEnum.TB) {
                    //再次创建

                } else if (statisticalMethodEnum == StatisticalMethodEnum.HQ || statisticalMethodEnum == StatisticalMethodEnum.HB) {

                }
            });


            /*ISqlField iDataSetField = iDataSet.getFieldMap().get(field.getFieldKey());
            if (iDataSetField == null) {
                return;
            }
            StatisticalMethodEnum statisticalMethodEnum = StatisticalMethodEnum.getEnum(field.getStatisticalMethod());



            if (statisticalMethodEnum == StatisticalMethodEnum.TQ || statisticalMethodEnum == StatisticalMethodEnum.TB) {
                cubeDomainExpr.getFieldList().add(new CubeDomainFieldExprBO(StatisticalMethodEnum.TQ, field.getAlias(), field.getFieldKey()))
                cubeResultFieldExprList.add(new CubeResultFieldExprBO())
            } else if (statisticalMethodEnum == StatisticalMethodEnum.HQ || statisticalMethodEnum == StatisticalMethodEnum.HB) {

            }*/
        });
    }


    public static CubeParserFactory getInstance(CubeRequest request, Map<String, IDataSet> dataSetMap) {
        return new CubeParserFactory(request, dataSetMap);
    }


    public SQLSelect createSqlExpr(CubeHandleModel cubeHandleModel) {
        IDataSet iDataSet = this.dataSetMap.get(cubeHandleModel.getDataSetCode());
        ISqlSelectParser sqlParser = SqlSelectParserFactory.getSqlParser(dbTypeEnum, iDataSet);
        sqlParser.
    }

    public SQLSelect createSqlExpr() {

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
    }
}
