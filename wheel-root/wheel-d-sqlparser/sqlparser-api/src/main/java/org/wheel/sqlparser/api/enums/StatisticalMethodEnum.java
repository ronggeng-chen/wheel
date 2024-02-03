package org.wheel.sqlparser.api.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-08-23-14:46
 * @Modified By:
 */
@Getter
@AllArgsConstructor
public enum StatisticalMethodEnum {

    SUM("sum", FieldTypeEnum.METRIC.getCode(), "求和"),

    COUNT("count", FieldTypeEnum.METRIC.getCode(), "计数"),

    AVG("avg", FieldTypeEnum.METRIC.getCode(), "平均值"),

    MAX("max", FieldTypeEnum.METRIC.getCode(), "最大值"),

    MIN("min", FieldTypeEnum.METRIC.getCode(), "最小值"),

    TQ("tq", FieldTypeEnum.METRIC.getCode(), "同期"),

    SQ("sq", FieldTypeEnum.METRIC.getCode(), "上期"),

    TB("tb", FieldTypeEnum.INDICATOR.getCode(), "同比"),

    HB("hb", FieldTypeEnum.INDICATOR.getCode(), "环比"),

    MEDIAN("median", FieldTypeEnum.METRIC.getCode(), "中位数"),

    CUSTOM("custom", FieldTypeEnum.INDICATOR.getCode(), "自定义");

    /**
     * 错误编码
     */
    private final String code;

    /**
     * 错误编码
     */
    private final String fieldType;

    /**
     * 提示用户信息
     */
    private final String name;

    public static String getFieldAlise(StatisticalMethodEnum methodEnum, String fieldType) {
        return StrUtil.format("{}_{}", methodEnum.code, fieldType);
    }


    public static StatisticalMethodEnum getEnum(String code) {
        return Arrays.stream(StatisticalMethodEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(CUSTOM);
    }
}
