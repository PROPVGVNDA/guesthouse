package com.university.guesthouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GuesthouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuesthouseApplication.class, args);
	}

}
