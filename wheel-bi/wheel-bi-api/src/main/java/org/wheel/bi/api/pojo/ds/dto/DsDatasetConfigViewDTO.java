package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)

public class DsDatasetConfigViewDTO {

    private List<DsDatasetConfigViewTableDTO> tableList;

    private List<DsDatasetConfigViewJoinDTO> joinList;

}
