package com.ideasStudio.website.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("con.ideasStudio.website")
public class WebConfig extends WebMvcConfigurerAdapter {
    // 在该类中配置文件上传解析器的 Bean 交给 Spring 管理即可
    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver  resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxInMemorySize(0); 
        resolver.setMaxUploadSizePerFile(100*1024*1024L); 
        resolver.setMaxUploadSize(1000*1024*1024L);
        return resolver;
    }
}
