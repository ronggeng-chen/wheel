package org.wheel.bi.api.pojo.ds.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryParserDomainConditionBO {

    private String leftExpr;

    private String operator;

    private List<Object> values;
}
