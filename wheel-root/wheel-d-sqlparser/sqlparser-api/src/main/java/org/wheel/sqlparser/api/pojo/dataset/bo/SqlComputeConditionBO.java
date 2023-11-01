package org.wheel.sqlparser.api.pojo.dataset.bo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.constants.SqlParserConstants;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/19 22:14
 */
@Data
@Accessors(chain = true)
public class SqlComputeConditionBO implements ISqlCondition {

    private String key;

    private String leftSnippet;

    private String operator;

    private String valueType;

    private List<Object> values;

    @Override
    public String getConditionType() {
        return SqlParserConstants.ConditionType.COMPUTE;
    }

    @Override
    public String getSqlSnippet() {
        return StrUtil.format("{} {}", this.leftSnippet, this.operator);
    }
}
