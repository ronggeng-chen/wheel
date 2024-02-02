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

    HQ("hq", FieldTypeEnum.METRIC.getCode(), "环期"),

    TB("tb", FieldTypeEnum.INDICATOR.getCode(), "同比"),

    HB("hb", FieldTypeEnum.INDICATOR.getCode(), "环比"),

    MEDIAN("median", FieldTypeEnum.METRIC.getCode(), "中位数"),

    CUSTOM("custom", FieldTypeEnum.INDICATOR.getCode(), "自定义"),

    EMPTY("empty", FieldTypeEnum.EMPTY.getCode(), "空对象");

    /**
     * 统计方法
     */
    private final String code;

    /**
     * 统计方法类型
     */
    private final String fieldType;

    /**
     * 名称
     */
    private final String name;


    public static StatisticalMethodEnum getEnum(String code) {
        return Arrays.stream(StatisticalMethodEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }

    public static String getFieldAlise(StatisticalMethodEnum statisticalMethodEnum, String domainKey, String alise) {
        String toAlise = "";
        if (StrUtil.isNotBlank(domainKey)) {
            toAlise += domainKey;
        }
        if (statisticalMethodEnum == EMPTY) {
            toAlise += "_" + StatisticalMethodEnum.CUSTOM.getCode() + "_" + alise;
        } else {
            toAlise += "_" + statisticalMethodEnum.getCode() + "_" + alise;
        }
        return toAlise;
    }

    public String getFieldSqlSnippet(StatisticalMethodEnum statisticalMethodEnum, String domainKey, String sqlSnippet) {
        if (statisticalMethodEnum == TQ || statisticalMethodEnum == HQ) {
            return sqlSnippet;
        } else if (statisticalMethodEnum == TB) {
            String tqFieldAlise = getFieldAlise(StatisticalMethodEnum.TQ, sqlContext);
            String customFieldAlise = getFieldAlise(StatisticalMethodEnum.CUSTOM, sqlContext);
            return StrUtil.format("({}-{})/{}", customFieldAlise, tqFieldAlise, tqFieldAlise);
        } else if (statisticalMethodEnum == HB) {
            String hqFieldAlise = getFieldAlise(StatisticalMethodEnum.HQ, sqlContext);
            String customFieldAlise = getFieldAlise(StatisticalMethodEnum.CUSTOM, sqlContext);
            return StrUtil.format("({}-{})/{}", customFieldAlise, hqFieldAlise, hqFieldAlise);
        } else if (statisticalMethodEnum == MEDIAN || statisticalMethodEnum == CUSTOM) {
            return sqlContext;
        } else {
            return StrUtil.format("{}({})", statisticalMethodEnum.getCode(), sqlContext);
        }
    }

}
