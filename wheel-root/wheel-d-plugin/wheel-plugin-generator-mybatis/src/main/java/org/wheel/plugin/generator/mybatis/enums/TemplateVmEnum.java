package org.wheel.plugin.generator.mybatis.enums;

import cn.hutool.core.util.StrUtil;
import org.wheel.plugin.generator.mybatis.util.ITemplateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: TemplatePojoEnum
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Getter
@AllArgsConstructor
public enum TemplateVmEnum implements ITemplateEnum {

    REQUEST("Request.java", true,"request", "/templates/vm/entityRequest.java.vm", new HashMap<>()),

    DTO("DTO.java", true,"dto", "/templates/vm/entityDTO.java.vm", new HashMap<>())
    ;
    /**
     * 模板编码
     */
    final String code;

    /**
     * 是否为其他模板
     */
    final Boolean isOtherTemplate;

    /**
     * 包名
     */
    final String toPackage;

    /**
     * 模板路径
     */
    final String templatePath;
    /**
     * params
     */
    final Map<String, Object> params;


    public static TemplateVmEnum getEnum(String code) {
        return Arrays.stream(values()).filter(aEnum -> StrUtil.equals(code, aEnum.getCode())).findAny().orElse(null);
    }
}
