package com.sciencecenter;

import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
@SpringBootApplication
public class SciencecenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SciencecenterApplication.class, args);
	}

}
