package org.wheel.bi.ds.util.cube;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Builder;
import org.wheel.bi.api.content.DsContext;
import org.wheel.bi.api.enums.ds.DsCubeFieldStatisticalMethodEnum;
import org.wheel.bi.api.enums.ds.DsDatasetFieldTypeEnum;
import org.wheel.bi.api.exception.DsException;
import org.wheel.bi.api.exception.enums.DsExceptionEnum;
import org.wheel.bi.api.pojo.ds.bo.DsCubeQueryParserBO;
import org.wheel.bi.api.pojo.ds.bo.DsCubeQueryParserDomainBO;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;
import org.wheel.bi.api.pojo.ds.request.DsCubeFieldRequest;
import org.wheel.bi.api.pojo.ds.request.DsCubeQueryRequest;
import org.wheel.bi.ds.util.datset.DatasetConfigBuilder;
import org.wheel.datasource.api.enums.DbTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class CubeQueryParserBuilder {

    private DsCubeQueryRequest request;

    private DsCubeQueryParserBO cubeQueryParser;

    CubeQueryParserBuilder init() {
        if (this.request == null) {
            throw new DsException(DsExceptionEnum.PARAM_ERROR);
        }
        cubeQueryParser = new DsCubeQueryParserBO();
        DbTypeEnum dbTypeEnum = DbTypeEnum.getEnum(request.getDbType());

        Map<String, List<DsCubeFieldRequest>> toHandleCubeDatasetMap = new HashMap<>();
        Map<String, DsDatasetFieldDTO> datasetFieldMap = new HashMap<>();
        this.request.getFields().forEach(cubeFieldRequest -> {
            DsDatasetFieldDTO datasetField = DsContext.getCacheDatasetField(cubeFieldRequest.getDatasetFieldCode());
            List<DsCubeFieldRequest> dsCubeFieldRequests = toHandleCubeDatasetMap.get(cubeFieldRequest.getCubeFieldCode());
            if (CollUtil.isEmpty(dsCubeFieldRequests)) {
                dsCubeFieldRequests = toHandleCubeDatasetMap.put(datasetField.getDatasetCode(), new ArrayList<>());
            }
            dsCubeFieldRequests.add(cubeFieldRequest);
            if (!datasetFieldMap.containsKey(cubeFieldRequest.getDatasetFieldCode())) {
                datasetFieldMap.put(cubeFieldRequest.getDatasetFieldCode(), datasetField);
            }
        });

        toHandleCubeDatasetMap.forEach((datasetCode, cubeFieldRequests) -> {
            DsDatasetDTO cacheDataset = DsContext.getCacheDataset(datasetCode);
            SQLSelect sqlSelect = DatasetConfigBuilder.builder().dataset(cacheDataset).dbTypeEnum(dbTypeEnum).build().init().getSqlSelect();
            cubeFieldRequests.forEach(cubeFieldRequest -> {
                DsDatasetFieldDTO datasetField = datasetFieldMap.get(cubeFieldRequest.getDatasetFieldCode());
                DsDatasetFieldTypeEnum datasetFieldTypeEnum = DsDatasetFieldTypeEnum.getEnum(datasetField.getFieldType());
                if (datasetFieldTypeEnum != DsDatasetFieldTypeEnum.DIMENSION) {

                } else {
                    DsCubeFieldStatisticalMethodEnum cubeFieldStatisticalMethodEnum = DsCubeFieldStatisticalMethodEnum.getEnum(cubeFieldRequest.getStatisticalMethod());
                    if (cubeFieldStatisticalMethodEnum == DsCubeFieldStatisticalMethodEnum.TQ || cubeFieldStatisticalMethodEnum == DsCubeFieldStatisticalMethodEnum.TB) {
                        SQLSelect clone = sqlSelect.clone();

                    } else if (cubeFieldStatisticalMethodEnum == DsCubeFieldStatisticalMethodEnum.SQ || cubeFieldStatisticalMethodEnum == DsCubeFieldStatisticalMethodEnum.HB) {
                        SQLSelect clone = sqlSelect.clone();
                        DsCubeQueryParserDomainBO tSqDomain = createTSqDomain(datasetCode);
                        tSqDomain.setFromSqlSelect(clone);
                        tSqDomain.getFieldMap().put(tSqDomain.getDomainCode()+"_");

                    } else if (cubeFieldStatisticalMethodEnum == DsCubeFieldStatisticalMethodEnum.MEDIAN) {

                    }
                }
            });

            DsCubeQueryParserDomainBO cubeQueryParserDomainBO = new DsCubeQueryParserDomainBO();
            cubeQueryParserDomainBO.setDomainCode(datasetCode);
            cubeQueryParserDomainBO.setConditions(null);
            cubeQueryParserDomainBO.setFields(null);
            cubeQueryParserDomainBO.setFromSqlSelect(sqlSelect);
        });
        return this;
    }

    private DsCubeQueryParserDomainBO getDomain(String domainCode) {
        return cubeQueryParser.getDomainMap().get(domainCode);
    }

    private DsCubeQueryParserDomainBO createTSqDomain(String datasetCode) {
        String sqDomainCode = "sq_" + datasetCode;
        DsCubeQueryParserDomainBO dsCubeQueryParserDomainBO = cubeQueryParser.getDomainMap().get(sqDomainCode);
        if (dsCubeQueryParserDomainBO != null) {
            dsCubeQueryParserDomainBO = cubeQueryParser.getDomainMap().put(sqDomainCode, new DsCubeQueryParserDomainBO());
        }
        return dsCubeQueryParserDomainBO;
    }

    private DsCubeQueryParserDomainBO createTSqDomainField(String datasetCode) {
        DsCubeQueryParserDomainBO tSqDomain = createTSqDomain(datasetCode);
        tSqDomain.getFieldMap()
        return dsCubeQueryParserDomainBO;
    }


}
