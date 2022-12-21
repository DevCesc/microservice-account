package com.bootcamp.service;

import com.bootcamp.event.DataClientCreatedEvent;
import com.bootcamp.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YankiEventService {

    @Autowired
    private AccountService accountService;

    @KafkaListener(
            topics = "topic-yanki-4" ,
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "grupo1")
    public void consumer(Event<?> event) {
        if (event.getClass().isAssignableFrom(DataClientCreatedEvent.class)) {
            accountService.saveAccountForYanki().subscribe();
        }
    }
}
