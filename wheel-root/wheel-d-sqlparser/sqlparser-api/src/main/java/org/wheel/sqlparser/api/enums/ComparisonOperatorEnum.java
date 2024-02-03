package org.wheel.sqlparser.api.enums;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/24 22:10
 */
@Getter
@AllArgsConstructor
public enum ComparisonOperatorEnum {

    EQ("eq",SQLBinaryOperator.Equality,"等于"),

    NOT_EQ("not_eq",SQLBinaryOperator.NotEqual,"不等于"),

    IN("in",null,"包含"),

    NOT_IN("not_in",null,"不包含"),

    IS_NULL("is_null",SQLBinaryOperator.Is,"为空"),

    NOT_NULL("not_null",SQLBinaryOperator.IsNot,"不为空"),

    GT("gt",SQLBinaryOperator.GreaterThan,"大于"),

    GTE("gte",SQLBinaryOperator.GreaterThanOrEqual,"大于等于"),

    LT("lt",SQLBinaryOperator.LessThan,"小于"),

    LTE("lte", SQLBinaryOperator.LessThanOrEqual,"小于等于"),

    BETWEEN("between", null,"在...之间"),

    LIKE("like",SQLBinaryOperator.Like,"模糊匹配"),

    EMPTY("empty",null,"空对象");

    private String code;

    private SQLBinaryOperator token;

    private String name;

    public static ComparisonOperatorEnum getEnum(String code) {
        return Arrays.stream(ComparisonOperatorEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }
}
