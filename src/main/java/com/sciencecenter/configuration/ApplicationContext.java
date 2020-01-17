package com.sciencecenter.configuration;

import com.sciencecenter.service.EmailDelService;
import com.sciencecenter.service.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

    @Bean
    public ValidationService validationService(){
        return new ValidationService();
    }

    @Bean
    public EmailDelService emailDelService(){
        return new EmailDelService();
    }

}
