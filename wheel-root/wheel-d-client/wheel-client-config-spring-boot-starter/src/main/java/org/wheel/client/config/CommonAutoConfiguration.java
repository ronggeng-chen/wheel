package org.wheel.client.config;


import cn.stylefeng.roses.kernel.wrapper.field.jackson.CustomJacksonIntrospector;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wheel.client.config.exception.CustomErrorAttributes;
import org.wheel.client.config.exception.GlobalExceptionHandler;

/**
 * <p><标题>：</p>
 *
 * <p><描述>：</p>
 *
 * @author: fudl
 * @time: 3/2/2020 3:25 PM
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "cn.stylefeng.**.mapper")
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        log.info(" >>>>>> 开始加载全局异常[GlobalExceptionHandler]");
        return new GlobalExceptionHandler();
    }

    /**
     * 重写系统的默认错误提示
     *
     * @author fengshuonan
     * @since 2020/12/16 15:36
     */
    @Bean
    public CustomErrorAttributes gunsErrorAttributes() {
        return new CustomErrorAttributes();
    }


    /**
     * json自定义序列化工具,long转string
     *
     * @author fengshuonan
     * @since 2020/12/13 17:16
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance).serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.annotationIntrospector(new CustomJacksonIntrospector());
        };
    }
}
