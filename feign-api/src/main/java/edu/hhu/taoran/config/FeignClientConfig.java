package edu.hhu.taoran.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }

}