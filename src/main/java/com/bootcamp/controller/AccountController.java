package com.bootcamp.controller;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.dto.Credit;
//import com.bootcamp.dto.Credit;
import com.bootcamp.dto.Transaction;
import com.bootcamp.entity.Account;
import com.bootcamp.service.AccountService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping(value = "/save/personal")
    public Mono<ResponseEntity<Account>> save(@RequestBody AccountDto accountDto){
        return accountService.savePersonal(accountDto)
                .map(e -> ResponseEntity
                        .created(URI.create("/api/v1/accounts".concat("/").concat(String.valueOf(e.getId()))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @GetMapping(value = "/getAllByIdClient/{id}")
    public Flux<Account> getAllByIdClient (@PathVariable("id") ObjectId id) {
        return accountService.getAllByIdClient(id);
    }

//    @GetMapping(value = "/getByIdClient/{idclient}/typeAccount/{typeaccount}")
    @GetMapping(value = "/getByIdClient/{id}/typeAccount/{type}")
    public Mono<ResponseEntity<Account>> findByIdClientAndTypeAccount (@PathVariable("id") ObjectId id,
                                                                       @PathVariable("type") String typeAccount) {
        return accountService.findByIdClientAndTypeAccount(id,typeAccount)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping(value = "/saveTypeClient")
    public Mono<Account> saveTypeClient (@RequestBody AccountDto accountDto){
        return accountService.saveTypeClient(accountDto);
    }

    @PostMapping(value = "/save/vip")
    public Mono<ResponseEntity<Account>> saveVip(@RequestBody Account account){

        Mono<Account> monoBody = Mono.just(account);
        Mono<Account> monoDB = accountService.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount());

        return monoDB.zipWith(monoBody, (db, a) -> {
                    db.setIdClient(a.getIdClient());
                    db.setNumberAccount(a.getNumberAccount());
                    return db;
                })
                .flatMap(accountService::save)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping(value = "/saveTransaction")
    public Mono<Account> saveTransaction(@RequestBody Transaction transaction){
    	return accountService.saveTransaction(transaction);
    }
    
    @GetMapping(value = "/getBalance/{codClie}/{numAccount}")
    public Mono<Account> getBalance(@PathVariable ObjectId codClie, @PathVariable String numAccount ){
    	return accountService.getBalanceByAccount(codClie,numAccount);
    }
   
    
    @PostMapping(value = "/payCredit")
    public Mono<Account> payCredit (@RequestBody Credit credit){
       return accountService.payCredicAccount(credit);
    }


    @PostMapping(value = "/save/yanki")
    public Mono<ResponseEntity<Account>> saveAccountForYanki(@RequestBody Account account){
        return accountService.saveAccountForYanki(account)
                .map(e -> ResponseEntity
                        .created(URI.create("/api/v1/accounts".concat("/").concat(String.valueOf(e.getId()))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

}
