package com.datazord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages={"com.datazord.*"})
public class TomatoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TomatoApplication.class, args);
	}

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
//		final Person johnAoe = new Person("john", "aoe", LocalDateTime.now(), "loser", 0);
//		final Person johnBoe = new Person("john", "boe", LocalDateTime.now(), "a bit of a loser", 10);
//		final Person johnCoe = new Person("john", "coe", LocalDateTime.now(), "average", 100);
//		final Person johnDoe = new Person("john", "doe", LocalDateTime.now(), "winner", 1000);
//
//		personRepository.saveAll(Flux.just(johnAoe, johnBoe, johnCoe, johnDoe)).subscribe();
//
//		personRepository.findByFirstName("john").log().map(Person::getSecondName).subscribe(System.out::println);
//
//		personRepository.findOneByFirstName("john").log().map(Person::getId).subscribe(System.out::println);
	}
}
