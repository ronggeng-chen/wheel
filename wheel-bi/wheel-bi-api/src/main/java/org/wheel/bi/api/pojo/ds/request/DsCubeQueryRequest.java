package org.wheel.bi.api.pojo.ds.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryRequest {

    private String queryCode;

    private String dbCode;

    private String dbType;

    private List<DsCubeFieldRequest> fields;

    private List<DsCubeConditionRequest> conditions;

    private List<DsCubeOrderRequest> orders;

    private DsCubePageRequest page;

}
