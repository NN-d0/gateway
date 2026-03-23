package com.radio.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Gateway 全局跨域配置
 *
 * 说明：
 * 1. 当前项目同时在 application.yml 和 Java 配置中定义了 CORS
 * 2. 本次先将 Java 配置与 application.yml 完全对齐
 * 3. 统一口径为：
 *    - 允许所有来源模式
 *    - 允许 GET/POST/PUT/DELETE/OPTIONS
 *    - 允许所有请求头
 *    - 暴露所有响应头
 *    - 不允许携带凭证
 *    - 预检缓存 3600 秒
 *
 * 这样可以先避免两套配置互相冲突。
 */
@Configuration
public class GatewayCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 与 application.yml 保持一致
        config.addAllowedOriginPattern("*");

        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.OPTIONS);

        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addExposedHeader(CorsConfiguration.ALL);

        // 关键修复：与 application.yml 保持一致，统一为 false
        config.setAllowCredentials(false);

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}