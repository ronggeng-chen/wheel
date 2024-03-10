package org.wheel.bi.api.enums.ds;

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
public enum DsCubeFieldStatisticalMethodEnum {

    SUM("sum", "求和"),

    COUNT("count", "计数"),

    AVG("avg", "平均值"),

    MAX("max", "最大值"),

    MIN("min", "最小值"),

    TQ("tq", "同期"),

    SQ("sq", "上期"),

    TB("tb", "同比"),

    HB("hb", "环比"),

    MEDIAN("median", "中位数"),

    CUSTOM("custom", "自定义");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String name;


    public static DsCubeFieldStatisticalMethodEnum getEnum(String code) {
        return Arrays.stream(DsCubeFieldStatisticalMethodEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(CUSTOM);
    }
}
