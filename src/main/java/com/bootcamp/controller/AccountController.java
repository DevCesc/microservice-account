package com.bootcamp.controller;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.entity.Account;
import com.bootcamp.service.AccountService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/getAllByIdClient/{id}")
    public Flux<Account> getAllByIdClient (@PathVariable("id") ObjectId id) {
        return accountService.getAllByIdClient(id);
    }


    @PostMapping(value = "/save")
    public Mono<Account> save (@RequestBody AccountDto accountDto){
        return accountService.save(accountDto);
    }


}
