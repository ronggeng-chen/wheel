package org.wheel.bi.api.enums.ds;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/8/27 1:11
 */
@AllArgsConstructor
@Getter
public enum DsSqlJoinTypeEnum {

    INNER_JOIN("inner_join", SQLJoinTableSource.JoinType.INNER_JOIN, "内连接"),

    LEFT_JOIN("left_join", SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN, "左连接"),

    RIGHT_JOIN("right_join", SQLJoinTableSource.JoinType.RIGHT_OUTER_JOIN, "右连接"),

    CROSS_JOIN("cross_join", SQLJoinTableSource.JoinType.CROSS_JOIN, "交叉连接"),

    EMPTY("empty", null, "空对象");

    /**
     * 编码
     */
    private final String code;
    /**
     * token
     */
    private final SQLJoinTableSource.JoinType token;

    /**
     * 名称
     */
    private final String name;


    public static DsSqlJoinTypeEnum getEnum(String code) {
        return Arrays.stream(DsSqlJoinTypeEnum.values()).filter(node -> StrUtil.equals(node.getCode(), code)).findAny().orElse(EMPTY);
    }

}
