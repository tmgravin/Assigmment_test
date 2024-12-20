package com.msp.assignment;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.msp.assignment.model")
@EnableJpaRepositories(basePackages = "com.msp.assignment.repository")
public class MspApplication {
	public static void main(String[] args) {
		// Load environment variables from .env file
		Dotenv dotenv = Dotenv.load();

		// Set system properties from .env file
		System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
		System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
		System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
		System.setProperty("S3_BUCKET_NAME", dotenv.get("S3_BUCKET_NAME"));
		System.setProperty("S3_BASE_URL", dotenv.get("S3_BASE_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DATABASE", dotenv.get("DATABASE"));
		System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
		System.setProperty("FRONTEND_URL", dotenv.get("FRONTEND_URL"));

		SpringApplication.run(MspApplication.class, args);
	}

}