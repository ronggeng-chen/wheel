package org.wheel.plugin.generator.mybatis;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import org.wheel.plugin.generator.mybatis.util.MybatisGeneratorUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * mybatis generator初始化配置文件 启动程序入口
 *
 * @className: MybatisGeneratorInitMojo
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Mojo(name = "init", requiresDependencyResolution = ResolutionScope.COMPILE)
public class MybatisGeneratorInitMojo extends AbstractMojo {

    @Parameter(property = "generator.path", defaultValue = "mybatis-generator.yml")
    private String path;

    @Component
    private MavenProject project;

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:17
     * @Description: run
     * @Params:
     * @return: void
     */
    @Override
    public void execute() {
        this.getLog().info("start processing ... ");
        File file = new File(MybatisGeneratorUtil.getConfigPath(path));
        if (file.exists()) {
            this.getLog().error(StrUtil.format("The file already exists | {}", file.getPath()));
        } else {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.writeFromStream(MybatisGeneratorUtil.getTemplateInputStream());
            this.getLog().info(StrUtil.format("File created successfully | {}", file.getPath()));
        }
    }
}
