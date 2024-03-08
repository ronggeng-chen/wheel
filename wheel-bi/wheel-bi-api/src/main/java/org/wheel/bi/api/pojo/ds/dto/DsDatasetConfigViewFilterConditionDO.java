package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 22:14
 */
@Getter
@Setter
@Accessors(chain = true)

public class DsDatasetConfigViewFilterConditionDO {

    private String tableAlise;

    private String fieldCode;

    private String leftExpr;

    private String operator;

    private String valueType;

    private List<Object> values;

}
