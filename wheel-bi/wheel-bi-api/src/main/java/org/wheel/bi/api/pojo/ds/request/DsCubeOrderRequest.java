package org.wheel.bi.api.pojo.ds.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeOrderRequest {

    private Integer orderNum;

    private String datasetFieldCode;

    private String orderType;
}
