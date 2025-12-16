package com.koreait.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@ComponentScan(basePackages = {"com.koreait.www.controller","com.koreait.www.service",
        "com.koreait.www.handler", "com.koreait.www.security", "com.koreait.www.exception"})
@EnableWebMvc
@EnableTransactionManagement
@EnableScheduling
@Configuration
public class ServletConfigration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 경로 설정
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) { 
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        registry.viewResolver(viewResolver);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }
}
