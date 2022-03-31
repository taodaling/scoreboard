package org.happyhour.scoreboard.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public CleanInterceptor cleanInterceptor() {
        return new CleanInterceptor();
    }
    @Bean
    public JWTInterceptor jWTInterceptor() {
        return new JWTInterceptor();
    }
    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cleanInterceptor());
        registry.addInterceptor(logInterceptor());
        registry.addInterceptor(jWTInterceptor());
    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
