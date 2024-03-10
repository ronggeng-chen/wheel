package org.wheel.bi.ds.util.datset;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;
import org.wheel.datasource.api.enums.DbTypeEnum;

import java.util.List;

public class DatasetConfigSqlParser extends AbstractDatasetConfigParser {

    public DatasetConfigSqlParser(DbTypeEnum dbTypeEnum, DsDatasetDTO dataset) {
        super(dbTypeEnum, dataset);
    }

    @Override
    public void init() {

    }

    @Override
    public SQLSelect getSQLSelect(List<DsDatasetFieldDTO> fields) {
        return null;
    }
}
