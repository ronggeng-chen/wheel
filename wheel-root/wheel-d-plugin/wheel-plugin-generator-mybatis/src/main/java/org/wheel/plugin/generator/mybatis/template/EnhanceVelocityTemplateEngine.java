package org.wheel.plugin.generator.mybatis.template;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.wheel.plugin.generator.mybatis.enums.TemplateVmEnum;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static org.wheel.plugin.generator.mybatis.util.MybatisGeneratorFactory.OTHER_PACKAGE;
import static org.wheel.plugin.generator.mybatis.util.MybatisGeneratorFactory.OTHER_PACKAGE_OUTPUT_DIR;

/*
 * @Author: aGeng
 * @Date: 2022/6/19 19:16
 * @Description:
 */
public final class EnhanceVelocityTemplateEngine extends VelocityTemplateEngine {

    private static final Log log = LogFactory.get();

    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        Object otherPackageOutputDir = objectMap.get(OTHER_PACKAGE_OUTPUT_DIR);
        String otherPath;
        if (otherPackageOutputDir != null) {
            otherPath = otherPackageOutputDir + File.separator + objectMap.get(OTHER_PACKAGE);
        } else {
            otherPath = this.getPathInfo(OutputFile.other);
        }
        customFile.forEach((key, value) -> {
            String toFilePath = getOutPath(key, otherPath, entityName);
            this.outputFile(new File(toFilePath), objectMap, value, Objects.requireNonNull(this.getConfigBuilder().getInjectionConfig()).isFileOverride());
            log.info("[自定义模板] 业务类型: other , 模板类型: {} , 路径:{}", key, toFilePath);
        });
    }


    public String getOutPath(String code, String otherPath, String entityName) {
        TemplateVmEnum templateVmEnum = TemplateVmEnum.getEnum(code);
        return String.format(otherPath + File.separator + templateVmEnum.getToPackage() + File.separator + entityName + "%s", code);
    }
}