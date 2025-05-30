package com.rapidforge;

import com.rapidforge.service.DependencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.rapidforge")
public class RapidforgeApplication {

	private static final Logger logger = LoggerFactory.getLogger(RapidforgeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RapidforgeApplication.class, args);
	}

	@Bean
	CommandLineRunner run(DependencyService parser) {
		return args -> {
			if (args.length > 0) {
				logger.info("Starting POM scan in directory: {}", args[0]);
				parser.scanPomDirectory(args[0]);
			} else {
				logger.warn("No directory path provided to scan POM files.");
			}
		};
	}
}
