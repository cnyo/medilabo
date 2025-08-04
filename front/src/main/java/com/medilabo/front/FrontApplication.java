package com.medilabo.front;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients("com.medilabo.front")
public class FrontApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(FrontApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		WebClient loadBalancedClient = ctx.getBean(WebClient.Builder.class).build();
		for(int i = 1; i <= 10; i++) {
			String response =
					loadBalancedClient.get().uri("http://localhost/hello")
							.retrieve().toEntity(String.class)
							.block().getBody();
			System.out.println(response);
		}
	}

}
