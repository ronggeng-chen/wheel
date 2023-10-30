package org.wheel.sqlparser.api.pojo.base;

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
public class BaseFilterBO {
    /**
     * 三元符
     */
    private String ternary;
    /**
     * 条件
     */
    IBaseCondition condition;

    /**
     * 子集
     */
    private List<BaseFilterBO> children;
}
