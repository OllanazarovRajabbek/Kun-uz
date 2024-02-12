package com.example.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/region/adm");
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/profile/adm");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/profile/*");
        bean.addUrlPatterns("/category/adm");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/articleType/adm");
        bean.addUrlPatterns("/articleType/adm/*");
        bean.addUrlPatterns("/article/adm");
        bean.addUrlPatterns("/article/adm/*");
        bean.addUrlPatterns("/attach/adm");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/email_history/adm");
        bean.addUrlPatterns("/sms_history/adm");
        bean.addUrlPatterns("/article/*");
        bean.addUrlPatterns("/article_like/*");
        bean.addUrlPatterns("/comment_like/*");
        bean.addUrlPatterns("/comment/any");
        bean.addUrlPatterns("/comment/any/*");
        bean.addUrlPatterns("/comment/adm");
        bean.addUrlPatterns("/comment/adm/*");
        bean.addUrlPatterns("/comment/adm/any/*");

        return bean;
    }

}
