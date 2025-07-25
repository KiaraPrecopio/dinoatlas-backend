package com.precopio.dinoatlasbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DinoatlasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DinoatlasBackendApplication.class, args);
	}

}
