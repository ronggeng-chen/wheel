package org.wheel.bi.api.pojo.ds.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeConditionRequest {

    String datasetFieldCode;

    String operator;

    List<Object> values;
}
