package com.example.login.paymentModuleApp;

import javax.validation.constraints.Email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Email
@ComponentScan({ "com.example.freeClimb.MessagingModule" })

@EnableJpaRepositories(basePackages = { "com.example.freeClimb.MessagingModule.repository" })
@EntityScan("com.example.*")

public class LoginApplicationModule extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplicationModule.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(LoginApplicationModule.class);
	}

}
