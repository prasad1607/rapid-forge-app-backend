package com.rapidforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.rapidforge")
public class RapidforgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RapidforgeApplication.class, args);
	}

}
