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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/getAllByIdClient/{id}")
    public Flux<Account> getAllByIdClient (@PathVariable("id") ObjectId id) {
        return accountService.getAllByIdClient(id);
    }

    @GetMapping(value = "/getByIdClient/{idclient}/typeAccount/{typeaccount}")
    public Mono<Account> findByIdClientAndtypeAccount (@PathVariable("idclient") String id,
                                                       @PathVariable("typeaccount") String typeAccount) {
        return accountService.findByIdClientAndtypeAccount(id, typeAccount);
    }


    @PostMapping(value = "/saveTypeClient")
    public Mono<Account> saveTypeClient (@RequestBody AccountDto accountDto){
        return accountService.saveTypeClient(accountDto);
    }

    @PostMapping(value = "/save/vip")
    public Mono<ResponseEntity<Account>> saveVip(@RequestBody Account account){

        Mono<Account> monoBody = Mono.just(account);
        Mono<Account> monoDB = accountService.findByIdClientAndtypeAccount(String.valueOf(account.getIdClient()), account.getTypeAccount());

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


}
