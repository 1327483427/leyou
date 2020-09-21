package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
       //初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许要跨域的域名，如果需要携带cookie 不能写*（代表所有域名都能跨域访问）
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        //是否允许携带cookie
        corsConfiguration.setAllowCredentials(true);
        //允许的方法 *为所有
        corsConfiguration.addAllowedMethod("*");
        //允许的头 *为所有
        corsConfiguration.addAllowedHeader("*");
        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource UrlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        UrlBasedCorsConfigurationSource.registerCorsConfiguration(
            "/**",corsConfiguration
        );
        //返回cors实例，参数：cors配置源对象
        return  new  CorsFilter(UrlBasedCorsConfigurationSource);
    }


}
