package org.wheel.bi.api.pojo.ds.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeFieldRequest {

    private String cubeFieldCode;

    private String datasetFieldCode;

    private String statisticalMethod;

}
