package org.wheel.bi.app;

import cn.stylefeng.roses.kernel.db.starter.GunsDataSourceAutoConfiguration;
import cn.stylefeng.roses.kernel.rule.context.ApplicationPropertiesContext;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * SpringBoot方式启动类
 */
@Slf4j
@MapperScan(basePackages = {"org.wheel.**.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.stylefeng", "org.wheel"}, exclude = {FlywayAutoConfiguration.class, GunsDataSourceAutoConfiguration.class})
@EnableCaching//Spring Cache 缓存
public class BIApplication {

    public static void main(String[] args) {
        SpringApplication.run(BIApplication.class, args);
        // 获取上下文
        ApplicationPropertiesContext context = ApplicationPropertiesContext.getInstance();
        log.info("[{}] is success! profile: [{}]", BIApplication.class.getSimpleName(), context.getProfile());
    }
}

