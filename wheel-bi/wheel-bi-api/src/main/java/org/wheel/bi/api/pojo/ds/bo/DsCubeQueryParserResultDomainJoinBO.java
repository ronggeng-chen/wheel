package org.wheel.bi.api.pojo.ds.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryParserResultDomainJoinBO {


    String leftExpr;

    String joinType;

    String rightExpr;

    DsCubeQueryParserResultDomainJoinBO nextJoin;
}
