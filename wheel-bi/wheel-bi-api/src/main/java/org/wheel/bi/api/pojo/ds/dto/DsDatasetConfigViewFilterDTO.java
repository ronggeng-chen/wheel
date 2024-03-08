package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigViewFilterDTO {

    private Integer orderNumber;

    private String sqlExpr;

    private String orderType;
}
