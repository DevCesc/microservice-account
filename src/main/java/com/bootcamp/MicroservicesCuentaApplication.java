package com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import reactivefeign.spring.config.EnableReactiveFeignClients;


@EnableReactiveFeignClients
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MicroservicesCuentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesCuentaApplication.class, args);
	}

}
