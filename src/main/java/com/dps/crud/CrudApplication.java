package com.dps.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class CrudApplication {
//	private static final Logger log = LoggerFactory.getLogger(CrudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
		log.info("Employee Management System Application started successfully!");
	}

//	@Bean
//	public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			if (userRepository.findByUsername("testuser").isEmpty()) {
//				User user = new User();
//				user.setUsername("testuser");
//				user.setPassword(passwordEncoder.encode("dps123!@#"));
//				user.setRole("USER");
//				userRepository.save(user);
//				log.info("Default user 'testuser' created with password 'dps123!@#'");
//			}
//		};
//	}

}