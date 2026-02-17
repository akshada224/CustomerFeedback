package com.CustomerFeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerFeebackManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerFeebackManagerApplication.class, args);
	}

	
	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner dataLoader(
			com.CustomerFeedback.repository.UserRepository userRepository,
			org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin") == null) {
				com.CustomerFeedback.model.User admin = new com.CustomerFeedback.model.User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin"));
				admin.setRole("ADMIN");
				admin.setEmail("admin@example.com");
				userRepository.save(admin);
				System.out.println("Default Admin created: username 'admin', password 'admin'");
			}
		};
	}

}