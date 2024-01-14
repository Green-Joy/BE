package com.spring.GreenJoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication()
public class GreenJoyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenJoyApplication.class, args);
	}

}
