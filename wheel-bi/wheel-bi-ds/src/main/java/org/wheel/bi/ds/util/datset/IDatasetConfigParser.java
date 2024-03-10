package org.wheel.bi.ds.util.datset;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetFieldDTO;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:48
 */
public interface IDatasetConfigParser {

    void init();

    SQLSelect getSQLSelect();

    SQLSelect getSQLSelect(List<DsDatasetFieldDTO> fields);
}
