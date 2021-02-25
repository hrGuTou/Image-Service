package com.instagclone.imageservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;

@Configuration
public class WebConfig{
    @Bean
    public Filter httpsEnforcerFilter(){
        return new HttpsEnforcer();
    }
}