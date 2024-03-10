package org.wheel.client.auth;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wheel.client.auth.security.TokenAndPermissionInterceptor;

import javax.annotation.Resource;

/**
 * spring mvc的配置
 */
@Configuration
@Import({cn.hutool.extra.spring.SpringUtil.class})
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private TokenAndPermissionInterceptor tokenAndPermissionInterceptor;

    /**
     * 重写系统的默认错误提示
     *
     * @author fengshuonan
     * @since 2020/12/16 15:36
     */


    /**
     * 配置项目拦截器
     *
     * @author fengshuonan
     * @since 2020/12/18 9:43
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAndPermissionInterceptor);
    }

    /**
     * 静态资源映射
     *
     * @author fengshuonan
     * @since 2021/1/16 21:45
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
        registry.addResourceHandler("/guns-devops/**").addResourceLocations("classpath:/guns-devops/");
    }


}
