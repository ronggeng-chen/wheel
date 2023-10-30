package org.wheel.plugin.generator.mybatis.pojo;

import lombok.Data;

import java.util.List;

/**
 * mybatis generator 配置文件
 *
 * @className: EnhanceVelocityTemplateEngine
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
@Data
public class MybatisGeneratorProperties {
    /**
     * 模板引擎
     */
    private String templateEngine;
    /**
     * 数据库
     */
    private Database database;
    /**
     * 配置设置
     */
    private Config config;
    /**
     * 生成模式
     */
    private List<Generator> generator;

    @Data
    public static class Database {
        /**
         * url
         */
        private String jdbcType;
        /**
         * url
         */
        private String url;
        /**
         * 模式名
         */
        private String schema;
        /**
         * 用户名
         */
        private String userName;
        /**
         * 密码
         */
        private String password;
    }

    @Data
    public static class Config {
        /**
         * 输出目录
         */
        private String outputDir;
        /**
         * swagger
         */
        private Boolean enableSwagger;
        /**
         * 包路径
         */
        private String packagePath;
        /**
         * xml生成路径
         */
        private String mapperXmlPackagePath;
        /**
         * 作者
         */
        private String author;
        /**
         * dao 包名
         */
        private String mapperPackage;
        /**
         * VO生成路径
         */
        private String otherPackage;
        /**
         * 生成路径
         */
        private String otherPackageOutputDir;
        /**
         * mapper xml 包名
         */
        private String mapperXmlPackage;
        /**
         * entity 包名
         */
        private String entityPackage;
        /**
         * service 包名
         */
        private String servicePackage;
        /**
         * service.impl 包名
         */
        private String serviceImplPackage;
        /**
         * controller 包名
         */
        private String controllerPackage;

    }

    @Data
    public static class Generator {
        /**
         * 是否启用
         */
        private boolean enable;
        /**
         * 删除前缀
         */
        private String filterPrefix;
        /**
         * 模块名
         */
        private String moduleName;
        /**
         * 表名
         */
        private String tableCode;
        /**
         * 表名
         */
        private String likeTable;
    }
}
