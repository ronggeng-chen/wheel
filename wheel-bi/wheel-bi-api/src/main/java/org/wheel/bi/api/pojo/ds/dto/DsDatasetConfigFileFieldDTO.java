package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigFileFieldDTO {

    private String index;

    private String syncFieldCode;

    private String syncFieldName;
}
