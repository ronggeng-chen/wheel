package org.wheel.sqlparser.sdk.cube;

import lombok.Builder;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.IDataSetSqlParser;
import org.wheel.sqlparser.api.pojo.base.bo.ISqlField;
import org.wheel.sqlparser.api.pojo.cube.bo.CubeParserBO;
import org.wheel.sqlparser.api.pojo.cube.request.CubeRequest;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;
import org.wheel.sqlparser.sdk.dataset.SqlSelectParserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
public class CubeParserBuilder {

    private Map<String, IDataSet> dataSetMap;

    private CubeParserBO cubeParser;

    CubeParserBuilder generateCubeParser(CubeRequest request) {
       /* CubeParserGenerateFactory instance = CubeParserGenerateFactory.getInstance(request);
        instance.generate();
        this.cubeParser = instance.getCubeParserBO();*/
        return this;
    }

    CubeParserBuilder aaaa() {
        DbTypeEnum dbTypeEnum = DbTypeEnum.getEnum(cubeParser.getDbType());
        cubeParser.getComputeDomains().forEach(domain -> {
            IDataSet iDataSet = dataSetMap.get(domain.getDataSetCode());
            IDataSetSqlParser sqlParser = SqlSelectParserFactory.getSqlParser(dbTypeEnum, iDataSet);
            sqlParser.init();
            List<ISqlField> selectSqlField = new ArrayList<>();
            domain.getFields().forEach(field -> {
                ISqlField iSqlField = iDataSet.getFieldMap().get(field.getFieldKey());
                selectSqlField.add(iSqlField);
            });
        });
        return this;
    }
}
