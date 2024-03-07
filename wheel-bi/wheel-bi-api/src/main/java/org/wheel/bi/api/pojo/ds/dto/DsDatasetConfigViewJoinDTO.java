package org.wheel.bi.api.pojo.ds.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)

public class DsDatasetConfigViewJoinDTO {
    /**
     * 主表别名
     */
    private String primaryTableAlias;
    /**
     * 主表键
     */
    private String primaryFieldCode;
    /**
     * 关联类型
     */
    private String joinType;
    /**
     * 附表别名
     */
    private String foreignTableAlias;
    /**
     * 附表主键
     */
    private String foreignFieldCode;
}
