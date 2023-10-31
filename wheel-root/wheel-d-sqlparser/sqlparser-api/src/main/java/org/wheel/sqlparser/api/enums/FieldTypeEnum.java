package org.wheel.sqlparser.api.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 异常定义类
 *
 * @author aGeng
 * @date 2022/3/9 9:59
 **/
@Getter
@AllArgsConstructor
public enum FieldTypeEnum {

    METRIC("metric", "度量"),

    DIMENSION("dimension", "维度"),

    INDICATOR("indicator", "指标"),

    EMPTY("empty", "空对象");

    /**
     * 错误编码
     */
    private final String code;

    /**
     * 提示用户信息
     */
    private final String name;


    public static FieldTypeEnum getEnum(String code) {
        return Arrays.stream(FieldTypeEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }

}
