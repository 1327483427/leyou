package com.leyou.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfiguration {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        //3) 允许的请求方式
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("POST");
        // 4）允许的头信息
        corsConfiguration.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", corsConfiguration);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
