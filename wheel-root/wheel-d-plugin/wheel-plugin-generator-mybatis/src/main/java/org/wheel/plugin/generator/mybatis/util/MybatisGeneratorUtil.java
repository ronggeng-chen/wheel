package org.wheel.plugin.generator.mybatis.util;

import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.InputStream;

/*
 * @Author: aGeng
 * @Date: 2022/6/19 18:19
 * @Description: 工具类
 */
public class MybatisGeneratorUtil {

    public static String TEMPLATE_NAME = "mybatis-generator.yml";

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:14
     * @Description: 获取maven项目根路径
     * @Params:
     * @return: java.lang.String
     */
    public static String getBasedir() {
        return System.getProperty("user.dir");
    }

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:13
     * @Description: 获取配置文件路径
     * @Params: configPath 配置路径
     * @return: java.lang.String
     */
    public static String getConfigPath(String configPath) throws RuntimeException {
        return StrUtil.concat(true, getBasedir(), "/src/main/resources/", configPath);
    }

    /*
     * @Author: aGeng
     * @Date: 2022/6/19 19:08
     * @Description: 校验配置文件路径
     * @Params:
     * @return: void
     */
    public static void checkConfigPath(String configPath) {
        if (!new File(configPath).exists()) {
            throw new RuntimeException("The file does not exist | configPath：" + configPath);
        }
    }


    /*
     * @Author: aGeng
     * @Date: 2022/6/19 18:21
     * @Description: 获取模板inputStream
     * @Params:
     * @return: java.io.InputStream
     */
    public static InputStream getTemplateInputStream() {
        return MybatisGeneratorUtil.class.getClassLoader().getResourceAsStream(TEMPLATE_NAME);
    }
}
