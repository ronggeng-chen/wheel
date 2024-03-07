package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigFileDTO {

    String fileCode;

    String fileName;

    String suffix;

    String syncTableCode;

}
