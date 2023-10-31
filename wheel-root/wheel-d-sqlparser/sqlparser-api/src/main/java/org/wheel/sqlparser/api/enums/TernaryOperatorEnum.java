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
 * @date 2023/8/24 22:37
 */
@Getter
@AllArgsConstructor
public enum TernaryOperatorEnum {

    AND("and",  SQLBinaryOperator.BooleanAnd,"与"),
    OR("or",  SQLBinaryOperator.BooleanAnd,"或"),
    NOT("not",SQLBinaryOperator.BooleanAnd,"非"),
    EMPTY("empty", null,"空对象");

    private String code;

    private SQLBinaryOperator token;

    private String name;

    public static TernaryOperatorEnum getEnum(String code) {
        return Arrays.stream(TernaryOperatorEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }
}
