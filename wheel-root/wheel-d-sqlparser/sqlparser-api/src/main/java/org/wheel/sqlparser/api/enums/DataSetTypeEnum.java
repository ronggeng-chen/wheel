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
 * @Date Created in 2023-11-02-14:05
 * @Modified By:
 */
@Getter
@AllArgsConstructor
public enum DataSetTypeEnum {

    VIEW("view", "视图"),

    FILE("file", "文件"),

    SQL("sql", "SQL脚本"),

    EMPTY("empty", "空对象");


    /**
     * 错误编码
     */
    private final String code;

    /**
     * 提示用户信息
     */
    private final String name;


    public static DataSetTypeEnum getEnum(String code) {
        return Arrays.stream(DataSetTypeEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }
}
