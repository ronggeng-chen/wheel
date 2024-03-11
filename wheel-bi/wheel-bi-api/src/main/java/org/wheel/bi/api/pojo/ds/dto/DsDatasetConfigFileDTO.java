package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DsDatasetConfigFileDTO {

    String fileCode;

    String fileName;

    String suffix;

    String syncTableCode;

    List<DsDatasetConfigFileFieldDTO> syncFields;

}
