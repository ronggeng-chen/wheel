package org.wheel.sqlparser.api.pojo.base;

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
public class SqlNormalConditionBO implements ISqlCondition {

    private String key;

    private String tableAlise;

    private String fieldCode;

    private String operator;

    private String valueType;

    private List<Object> values;

    @Override
    public String getConditionType() {
        return SqlParserConstants.ConditionType.NORMAL;
    }

    @Override
    public String getSqlSnippet() {
        return StrUtil.format("{}.{} {} ",this.tableAlise,this.fieldCode,this.operator);
    }
}
