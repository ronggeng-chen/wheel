package org.wheel.plugin.generator.mybatis.util;

import java.util.Map;

/**
 * @className: ITemplateEnum
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
public interface ITemplateEnum {
    /**
     * 是否为其他模板
     * @return
     */
    Boolean getIsOtherTemplate();
    /**
     * 模板编码
     */
    String getCode();

    /**
     * 包名
     */
    String getToPackage();

    /**
     * 模板路径
     */
    String getTemplatePath();


    /**
     * 模板路径
     */
    Map<String,Object> getParams();
}
