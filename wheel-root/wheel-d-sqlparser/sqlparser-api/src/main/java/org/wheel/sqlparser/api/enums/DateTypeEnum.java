package org.wheel.sqlparser.api.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DateTypeEnum {

    YEAR("year", "年", "yyyy"),

    MONTH("month", "月", "yyyyMM"),

    DAY("day", "日", "yyyyMMdd"),

    EMPTY("empty", "空", "");

    private final String code;

    private final String name;

    private final String format;

    public static DateTypeEnum getEnum(String code) {
        return Arrays.stream(DateTypeEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }

}
