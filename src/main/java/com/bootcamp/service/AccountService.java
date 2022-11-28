package com.bootcamp.service;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.entity.Account;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> save (AccountDto accountDto);

    Flux<Account> getAllByIdClient (ObjectId id);

}
