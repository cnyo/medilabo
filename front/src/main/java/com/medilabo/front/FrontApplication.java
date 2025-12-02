package com.medilabo.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FrontApplication {
	private static final String BASE_URI = "http://gateway:9001";

	public static void main(String[] args) {
		SpringApplication.run(FrontApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl(BASE_URI).build();
	}
}
