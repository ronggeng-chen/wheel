package org.wheel.bi.api.pojo.ds.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 22:14
 */
@Data
@Accessors(chain = true)
public class DsDatasetFilterBO {
    //(a == 1 and 12312 ) or asdas
    /**
     * 三元符
     */
    private String ternary;
    /**
     * 条件
     */
    //ISqlCondition condition;

    /**
     * 子集
     */
    private List<DsDatasetFilterBO> children;
}

