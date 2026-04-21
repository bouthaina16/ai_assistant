package com.example.ai_assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.ai_assistant", "com.stock.ai.stockmovement"})
@EntityScan(basePackages = {"com.stock.ai.stockmovement.entity"})
@EnableJpaRepositories(basePackages = {"com.stock.ai.stockmovement.repository"})
public class AiAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiAssistantApplication.class, args);
	}

}
