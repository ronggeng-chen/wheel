package org.wheel.bi.ds.util.datset;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.extern.slf4j.Slf4j;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.datasource.api.enums.DbTypeEnum;

@Slf4j
public abstract class AbstractDatasetConfigParser implements IDatasetConfigParser {
    protected DbTypeEnum dbTypeEnum;

    protected SQLSelect sqlSelect;

    protected DsDatasetDTO dataset;

    public AbstractDatasetConfigParser(DbTypeEnum dbTypeEnum, DsDatasetDTO dataset) {
        this.dbTypeEnum = dbTypeEnum;
        this.dataset = dataset;
    }


    @Override
    public SQLSelect getSQLSelect() {
        if (this.sqlSelect == null) {
            this.init();
        }
        return this.sqlSelect;
    }

    @Override
    public String toString() {
        return getSQLSelect().toString();
    }
}