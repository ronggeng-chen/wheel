package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigViewOrderDTO {

    private String orderNum;

    private String tableAlise;

    private String fieldCode;

    private String sqlExpr;

    private String orderType;
}
