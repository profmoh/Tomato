package com.datazord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.datazord.utils.ScheduleJob;

@EnableReactiveMongoRepositories
@SpringBootApplication(scanBasePackages = { "com.datazord.*" })
public class TomatoApplication implements CommandLineRunner {

	@Value("${job.timeInterval:5000}")
	private Long timeInterval;

	public static void main(String[] args) {
		SpringApplication.run(TomatoApplication.class, args);
	}

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		new ScheduleJob(timeInterval.longValue());

//		log.info("start data initialization  ...");
//		this.userRepository
//		.deleteAll().thenMany(Flux.just("user", "admin").flatMap(username -> {
//			List<String> roles = "user".equals(username) ? Arrays.asList("ROLE_USER")
//					: Arrays.asList("ROLE_USER", "ROLE_ADMIN");
//
//			com.datazord.model.User user = com.datazord.model.User.builder().roles(roles).username(username).password(passwordEncoder().encode("password"))
//					.email(username + "@example.com").build();
//			return this.userRepository.save(user);
//		})).log().subscribe(null, null, () -> log.info("done users initialization..."));
	}
}
