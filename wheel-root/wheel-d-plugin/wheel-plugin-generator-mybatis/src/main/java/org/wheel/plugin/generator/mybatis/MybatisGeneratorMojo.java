package org.wheel.plugin.generator.mybatis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.yaml.YamlUtil;
import org.wheel.plugin.generator.mybatis.util.MybatisGeneratorFactory;
import org.wheel.plugin.generator.mybatis.pojo.MybatisGeneratorProperties;
import org.wheel.plugin.generator.mybatis.util.MybatisGeneratorUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * mybatis generator 启动程序入口
 *
 * @className: MybatisGeneratorMojo
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Mojo(name = "generator", requiresDependencyResolution = ResolutionScope.COMPILE)
public class MybatisGeneratorMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Parameter(property = "generator.path", defaultValue = "mybatis-generator.yml")
    private String path;

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:35
     * @Description: run @Params:
     * @return: void
     */
    @Override
    public void execute() {

        this.getLog().info("start processing ... ");
        String filePath = MybatisGeneratorUtil.getConfigPath(path);
        MybatisGeneratorUtil.checkConfigPath(filePath);
        this.getLog().info(StrUtil.format("load the configuration file ... | {}", filePath));
        MybatisGeneratorProperties properties = YamlUtil.loadByPath(filePath, MybatisGeneratorProperties.class);
        MybatisGeneratorFactory mybatisGeneratorFactory = MybatisGeneratorFactory.getInstance(properties);
        this.getLog().info(StrUtil.format("Parse the configuration file | {}", filePath));
        mybatisGeneratorFactory.check();
        this.getLog().info("execute the mybatis generator");
        mybatisGeneratorFactory.execute();
        this.getLog().info("execution successfully");
    }
}
