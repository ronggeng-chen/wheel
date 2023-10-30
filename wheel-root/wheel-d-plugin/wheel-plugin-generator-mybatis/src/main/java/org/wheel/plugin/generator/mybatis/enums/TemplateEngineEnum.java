package org.wheel.plugin.generator.mybatis.enums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.wheel.plugin.generator.mybatis.template.EnhanceVelocityTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 模板引擎
 *
 * @className: TemplateEngineEnum
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Getter
@AllArgsConstructor
public enum TemplateEngineEnum {

    VM("vm", "/templates/vm", new EnhanceVelocityTemplateEngine());
    /**
     * 模板编码
     */
    private final String code;
    /**
     * 模板路径
     */
    private final String templatePath;
    
    /**
     * 模板
     */
    private final AbstractTemplateEngine templateEngine;

    public static TemplateEngineEnum getEnum(String code) {
        return Arrays.stream(values()).filter(aEnum -> StrUtil.equals(code, aEnum.getCode())).findAny().orElse(VM);
    }
}
