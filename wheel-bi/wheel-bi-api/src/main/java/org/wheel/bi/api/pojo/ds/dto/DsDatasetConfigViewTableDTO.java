package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)

public class DsDatasetConfigViewTableDTO {

    private Boolean isMain;

    private String tableAlias;

    private String tableCode;

    private String tableName;
}
