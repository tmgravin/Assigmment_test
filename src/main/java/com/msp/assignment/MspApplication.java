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

		// Set S3 Credential environment variables as system properties
		System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
		System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
		System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
		System.setProperty("S3_BUCKET_NAME", dotenv.get("S3_BUCKET_NAME"));

		// Set Database Resource environment variables as system properties
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_DRIVER", dotenv.get("DB_DRIVER"));

		// Set Email Resource environment variables as system properties
		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));

		// Set Swagar Resource environment variables as system properties
		System.setProperty("SWAGAR_PATH", dotenv.get("SWAGAR_PATH"));
		System.setProperty("SWAGAR_DOCS", dotenv.get("SWAGAR_DOCS"));

		SpringApplication.run(MspApplication.class, args);
	}

}