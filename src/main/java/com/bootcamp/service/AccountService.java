package com.bootcamp.service;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.dto.Credit;
//import com.bootcamp.dto.Credit;
import com.bootcamp.dto.Transaction;
import com.bootcamp.entity.Account;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> saveTypeClient (AccountDto accountDto);

    Mono<Account> save (Account account);

    Mono<Account> findByIdClientAndtypeAccount (String id, String typeAccount);

    Flux<Account> getAllByIdClient (ObjectId id);
    
    Mono<Account> saveTransaction(Transaction transaction);
    
    Mono<Account> updateAccount(Account accountDto);
    
    Mono<Account> getAccount(AccountDto accountDto);
    
    Mono<Account> getBalanceByAccount(ObjectId idCli,String numberAccount);
    
    
    Mono<Account> getAccountbyNumAccount(String numaccountDto);
    
    Mono<Account> payCredicAccount(Credit credit);
    
    Mono<Account> saveTransactionCredit(AccountDto numberAccount, Double amount);
    
    Mono<Account> saveTransactionRetirement(AccountDto numberAccount, Double amount);
    
    Mono<Account> saveTransactionDeposit(AccountDto numberAccount, Double amount);
    
    Mono<Account> saveTransactionTransfer(AccountDto numberAccount, Double amount,String sourceAccount);
    
  

}
