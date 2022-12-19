package com.bootcamp.clients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bootcamp.dto.TransactionRest;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "microservice-transaction")
public interface TransactionClientRest {

	@PostMapping(value = "/saveTransaction")
	public Mono<TransactionRest> save(@RequestBody TransactionRest transaction);
	
}
