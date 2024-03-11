package org.wheel.bi.api.enums.ds;

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
public enum DsDatasetFieldTypeEnum {

    METRIC("metric", "度量"),

    DIMENSION("dimension", "维度"),

    EMPTY("empty", "空对象");

    /**
     * 错误编码
     */
    private final String code;

    /**
     * 提示用户信息
     */
    private final String name;


    public static DsDatasetFieldTypeEnum getEnum(String code) {
        return Arrays.stream(DsDatasetFieldTypeEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }

}
