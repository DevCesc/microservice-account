package com.bootcamp.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bootcamp.dto.TransactionRest;

import reactor.core.publisher.Mono;

@FeignClient(name = "microservice-transaction")
public interface TransactionClientRest {

	@PostMapping(value = "/saveTransaction")
	public Mono<TransactionRest> save(@RequestBody TransactionRest transaction);
	
}
