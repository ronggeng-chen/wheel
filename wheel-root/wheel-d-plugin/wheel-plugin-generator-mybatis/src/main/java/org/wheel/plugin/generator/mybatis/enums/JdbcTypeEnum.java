package org.wheel.plugin.generator.mybatis.enums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.AbstractDbQuery;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.BaseKeyWordsHandler;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * jdbc枚举
 *
 * @className: JdbcEnum
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Getter
@AllArgsConstructor
public enum JdbcTypeEnum {

    MYSQL("mysql",new MySqlQuery(), new MySqlTypeConvert(), new MySqlKeyWordsHandler());

    private final String jdbcType;

    private final AbstractDbQuery abstractDbQuery;

    private final ITypeConvert typeConvert;

    private final BaseKeyWordsHandler keyWordsHandler;

    public static JdbcTypeEnum getEnum(String jdbcType) {
        return Arrays.stream(values()).filter(aEnum -> StrUtil.equals(jdbcType, aEnum.getJdbcType())).findAny().orElse(null);
    }

}

