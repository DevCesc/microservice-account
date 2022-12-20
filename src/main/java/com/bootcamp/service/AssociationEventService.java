package com.bootcamp.service;

import org.springframework.stereotype.Component;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.entity.Account;
import com.bootcamp.event.DataClientCreatedEvent;
import com.bootcamp.event.Event;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


@Slf4j
@Component
public class AssociationEventService {
	
	@Autowired
	private AccountService accountService; 

	@KafkaListener(
			topics = "topic-yanki-2" ,
			containerFactory = "kafkaListenerContainerFactory",
	groupId = "grupo1")
	public void consumer(Event<?> event) {
		if (event.getClass().isAssignableFrom(DataClientCreatedEvent.class)) {
			DataClientCreatedEvent createdEvent = (DataClientCreatedEvent) event;
			log.info("Received account created event .... with Id={}, data={}",
					createdEvent.getId(),
					new ObjectId(createdEvent.getData().getIdYanki()));
		Mono<Account> monoAccount =	accountService.findPrimaryAccount(new ObjectId(createdEvent.getData().getIdClient()));
		Mono<Account> accountYanki = accountService.findAcountByIdClient(new ObjectId(createdEvent.getData().getIdYanki()));
		Account objAccount = monoAccount.block();
		Account objYanki = accountYanki.block();
		System.out.println("numberAccount: "+monoAccount.block());
		System.out.println("numberAccountYanki: "+accountYanki.block());
		AccountDto accountDto = new AccountDto();
		accountDto.setIdClient(objYanki.getIdClient());
		accountDto.setNumberAccount(objYanki.getNumberAccount());

		accountService.saveTransactionTransfer(accountDto,objAccount.getBalance(),objAccount.getNumberAccount()).subscribe();
		
		}

	}
	
}
