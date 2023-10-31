package org.wheel.sqlparser.api.pojo.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 22:04
 */
@Getter
@Setter
@Accessors(chain = true)
public class SqlTableJoinBO {
    private String joinKey;

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
