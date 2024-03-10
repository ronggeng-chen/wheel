package org.wheel.bi.ds.util.datset;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Builder;
import lombok.Getter;
import org.wheel.bi.api.enums.ds.DsDatasetTypeEnum;
import org.wheel.bi.api.exception.DsException;
import org.wheel.bi.api.exception.enums.DsExceptionEnum;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.datasource.api.enums.DbTypeEnum;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-11-02-13:53
 * @Modified By:
 */
@Builder
public class DatasetConfigBuilder {

    private DbTypeEnum dbTypeEnum;

    private DsDatasetDTO dataset;

    @Getter
    private SQLSelect sqlSelect;

    @Getter
    private IDatasetConfigParser configParser;

    public DatasetConfigBuilder init() {
        DsDatasetTypeEnum dataSetTypeEnum = DsDatasetTypeEnum.getEnum(dataset.getDatsetType());
        if (dataSetTypeEnum == DsDatasetTypeEnum.VIEW) {
            this.configParser = new DatasetConfigViewParser(dbTypeEnum, dataset);
        } else if (dataSetTypeEnum == DsDatasetTypeEnum.SQL) {
            this.configParser = new DatasetConfigSqlParser(dbTypeEnum, dataset);
        } else if (dataSetTypeEnum == DsDatasetTypeEnum.FILE) {
            this.configParser = new DatasetConfigFileParser(dbTypeEnum, dataset);
        } else {
            throw new DsException(DsExceptionEnum.DATASET_01, dataset.getDatsetType());
        }
        return this;
    }
}
