package org.wheel.plugin.generator.mybatis.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.wheel.plugin.generator.mybatis.enums.JdbcTypeEnum;
import org.wheel.plugin.generator.mybatis.enums.TemplateEngineEnum;
import org.wheel.plugin.generator.mybatis.enums.TemplateVmEnum;
import org.wheel.plugin.generator.mybatis.pojo.MybatisGeneratorProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MybatisGeneratorUtil
 * @description:
 * @author: aGeng
 * @date: 2023/2/18
 **/
public class MybatisGeneratorFactory {
    /**
     * 日志
     */
    private Log log;
    /**
     * 配置文件
     */
    private MybatisGeneratorProperties properties;
    /**
     * 文件位置
     */
    private String userDir;
    public static final String OTHER_PACKAGE = "OTHER_PACKAGE";

    public static final String OTHER_PACKAGE_OUTPUT_DIR = "OTHER_PACKAGE_OUTPUT_DIR";
    /**
     * 模板枚举
     */
    private ITemplateEnum templateEnum;

    private MybatisGeneratorFactory() {
    }

    private MybatisGeneratorFactory(MybatisGeneratorProperties properties) {
        this.log = LogFactory.get();
        this.userDir = System.getProperty("user.dir");
        this.properties = properties;
    }

    public static MybatisGeneratorFactory getInstance(MybatisGeneratorProperties properties) {
        return new MybatisGeneratorFactory(properties);
    }

    /**
     * 检查校验参数
     *
     * @author aGeng
     * @date 21:25 2023/2/18
     **/
    public void check() throws RuntimeException {
        if (this.properties.getDatabase() == null) {
            throw new RuntimeException("properties database is not null");
        }
        if (this.properties.getGenerator() == null) {
            throw new RuntimeException("properties generator is not null");
        }
    }

    /**
     * 执行
     *
     * @author aGeng
     * @date 21:25 2023/2/18
     **/
    public void execute() {
        JdbcTypeEnum jdbcTypeEnum = JdbcTypeEnum.getEnum(this.properties.getDatabase().getJdbcType());
        if (jdbcTypeEnum == null) {
            this.log.error("jdbcType invalid attribute");
            return;
        }
        TemplateEngineEnum templateEngine = TemplateEngineEnum.getEnum(this.properties.getTemplateEngine());
        ITemplateEnum[] templateEnums = getTemplateEnums(templateEngine);
        this.properties.getGenerator().forEach(generator -> {
            log.info("Process grouping rules ... | enable:{}  rule : {}   module : {}   table : {}", generator.isEnable(), generator.getLikeTable(), generator.getModuleName(), generator.getTableCode());
            if (!generator.isEnable()) {
                return;
            }
            log.info("creating ...");
            FastAutoGenerator.create(getDataSourceConfig(jdbcTypeEnum))
                    .globalConfig(this::setGlobalConfig)
                    .packageConfig(builder -> setPackageConfig(builder, generator.getModuleName()))
                    .strategyConfig(builder -> setStrategyConfig(builder,
                            generator.getFilterPrefix(),
                            generator.getTableCode(),
                            generator.getLikeTable()
                    ))
                    .injectionConfig(builder -> setInjectionConfig(builder, templateEnums))
                    .templateEngine(templateEngine.getTemplateEngine())
                    .templateConfig(builder -> setTemplateConfig(builder, templateEngine))
                    .execute();
            log.info("created successfully");
        });

    }

    private ITemplateEnum[] getTemplateEnums(TemplateEngineEnum templateEngine) {
        if (templateEngine == TemplateEngineEnum.VM) {
            return TemplateVmEnum.values();
        }
        return TemplateVmEnum.values();
    }

    /**
     * 获取DataSource配置
     *
     * @param jdbcTypeEnum
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder
     * @author aGeng
     * @date 21:31 2023/2/18
     **/
    private DataSourceConfig.Builder getDataSourceConfig(JdbcTypeEnum jdbcTypeEnum) {
        return new DataSourceConfig.Builder(
                //url
                this.properties.getDatabase().getUrl(),
                //用户
                this.properties.getDatabase().getUserName(),
                //密码
                this.properties.getDatabase().getPassword())
                //数据库 class
                .dbQuery(jdbcTypeEnum.getAbstractDbQuery())
                //模式名
                .schema(this.properties.getDatabase().getSchema())
                //数据库类型转换器 class
                .typeConvert(jdbcTypeEnum.getTypeConvert())
                //数据库关键词转换器 class
                .keyWordsHandler(jdbcTypeEnum.getKeyWordsHandler());
    }

    /**
     * 设置全局配置
     *
     * @param builder
     * @author aGeng
     * @date 21:31 2023/2/18
     **/
    private void setGlobalConfig(GlobalConfig.Builder builder) {

        builder.disableOpenDir()
                //输出目录
                .outputDir(StrUtil.format("{}/src/main/java/", StrUtil.isNotBlank(properties.getConfig().getOutputDir()) ? properties.getConfig().getOutputDir() : this.userDir))
                //作者
                .author(properties.getConfig().getAuthor())
                //生成 时间类型  TIME_PACK: java.time.LocalDateTime ONLY_DATE: java.util.Date ONLY_DATE:java.sql.Date
                .dateType(DateType.ONLY_DATE)
                //注释日期
                .commentDate("yyyy-MM-dd HH:mm:ss");
        if (BooleanUtil.isTrue(properties.getConfig().getEnableSwagger())) {
            builder.disableOpenDir().enableSwagger();
        }
    }

    /**
     * 设置包配置
     *
     * @param builder
     * @param moduleName
     * @author aGeng
     * @date 21:35 2023/2/18
     **/
    private void setPackageConfig(PackageConfig.Builder builder, String moduleName) {
        builder
                //父包名
                .parent(this.properties.getConfig().getPackagePath())
                //父包模块名
                .moduleName(moduleName)
                //实体类包名
                .entity(this.properties.getConfig().getEntityPackage())
                //Service 包名
                .service(this.properties.getConfig().getServicePackage())
                //Service Impl 包名
                .serviceImpl(this.properties.getConfig().getServiceImplPackage())
                //Other
                .other(this.properties.getConfig().getOtherPackage())
                //Mapper 包名
                .mapper(this.properties.getConfig().getMapperPackage())
                //Mapper XML 包名
                .xml(this.properties.getConfig().getMapperXmlPackage())
                //Controller 包名
                .controller(this.properties.getConfig().getControllerPackage());
    }


    /**
     * 设置模板配置
     *
     * @param builder
     * @author aGeng
     * @date 21:39 2023/2/18
     **/
    private void setTemplateConfig(TemplateConfig.Builder builder, TemplateEngineEnum templateEngine) {
        builder
                //禁用所有模板
                .disable(TemplateType.ENTITY)
                //实体类模板
                .entity(StrUtil.format("{}/entity.java", templateEngine.getTemplatePath()))
                //接口模板
                .service(StrUtil.format("{}/service.java", templateEngine.getTemplatePath()))
                //接口实现模板
                .serviceImpl(StrUtil.format("{}/serviceImpl.java", templateEngine.getTemplatePath()))
                //设置 mapper 模板路径
                .mapper(StrUtil.format("{}/mapper.java", templateEngine.getTemplatePath()))
                //设置 mapperXml 模板路径
                .xml(StrUtil.format("{}/mapper.xml", templateEngine.getTemplatePath()))
                //设置 controller 模板路径
                .controller(StrUtil.format("{}/controller.java", templateEngine.getTemplatePath()));
    }

    /**
     * 设置注入配置
     *
     * @param builder
     * @author aGeng
     * @date 21:39 2023/2/18
     **/
    private void setInjectionConfig(InjectionConfig.Builder builder, ITemplateEnum[] templateEnums) {
        Map<String, String> customFile = new HashMap<>();
        Map<String, Object> customMap = new HashMap<>();
        customMap.put(OTHER_PACKAGE, properties.getConfig().getOtherPackage());
        customMap.put(OTHER_PACKAGE_OUTPUT_DIR, properties.getConfig().getOtherPackageOutputDir());
        for (ITemplateEnum value : templateEnums) {
            customFile.put(value.getCode(), value.getTemplatePath());
            customMap.putAll(value.getParams());
        }
        builder.beforeOutputFile((tableInfo, objectMap) -> {
                    this.log.info("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                }).customMap(customMap)
                .customFile(customFile);
    }

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:47
     * @Description: 设置个性化配置
     * @Params:
     * @return: void
     */
    private void setStrategyConfig(StrategyConfig.Builder builder,String filterPrefix,String tableCodeListStr, String likeTable) {

        builder
                //跳过视图
                .enableSkipView()
                // ####设置实体类
                .entityBuilder()
                //开启链式模型
                .enableChainModel()
                //表命名策略
                .naming(NamingStrategy.underline_to_camel)
                //字段命名策略
                .columnNaming(NamingStrategy.underline_to_camel)
                //启用Lombox
                .enableLombok()
                //主键规则
                .idType(IdType.ASSIGN_UUID)
                //####设置controller
                .controllerBuilder()
                //生成@RestController
                .enableRestStyle()
                //####设置mapper
                .mapperBuilder()
                //生成@mapper
                .enableMapperAnnotation();
        if(StrUtil.isNotBlank(filterPrefix)){
            builder.addTablePrefix(filterPrefix);
        }
        if(StrUtil.isNotBlank(tableCodeListStr)){
            builder.addInclude(StrUtil.splitToArray(tableCodeListStr, ','));
        }
        if(StrUtil.isNotBlank(likeTable)){
            builder.likeTable(new LikeTable(likeTable));
        }
        builder.build();
    }


}
