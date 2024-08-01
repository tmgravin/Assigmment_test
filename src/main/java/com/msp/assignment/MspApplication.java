package com.msp.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.msp.assignment.model")
@EnableJpaRepositories(basePackages = "com.msp.assignment.repository")
public class MspApplication {
	public static void main(String[] args) {
		SpringApplication.run(MspApplication.class, args);
	}

}