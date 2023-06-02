package com.example.kino.config;

import com.example.kino.filter.RoleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<RoleFilter> roleBasedFilterRegistrationBean() {
        FilterRegistrationBean<RoleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RoleFilter());
        registrationBean.addUrlPatterns("/users/*");
        return registrationBean;
    }

}