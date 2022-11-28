package com.bootcamp.service;

import com.bootcamp.dto.AccountDto;
import com.bootcamp.dto.Transaction;
import com.bootcamp.entity.Account;
import com.bootcamp.repository.AccountRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Flux<Account> getAllByIdClient(ObjectId id) {

        Flux<Account> accountFlux = accountRepository.findAllByIdClient(id);
        return accountFlux;
    }


    @Override
    public Mono<Account> save(AccountDto accountDto) {

        switch (accountDto.getTypeClient()) {
            case "Personal":

                Flux<Account> accountFlux = getAllByIdClient(accountDto.getIdClient())
                        .filter(x -> x.getTypeAccount().contains(accountDto.getTypeAccount()));

                Account accountFlux1 = accountFlux.blockFirst();

                if (Objects.isNull(accountFlux1)) {
                    Account account = new Account();
                    account.setIdClient(accountDto.getIdClient());
                    account.setTypeAccount(accountDto.getTypeAccount());
                    account.setNumberAccount(accountDto.getNumberAccount());
                    account.setBalance(accountDto.getBalance());
                    account.setLimit(accountDto.getLimit());
                    account.setDebt(accountDto.getDebt());
                    return accountRepository.save(account);
                } else if (accountFlux1.getTypeAccount().equals(accountDto.getTypeAccount())) {
                    return Mono.error(new Exception("CLIENTE YA TIENE UNA CUENTA: " + accountDto.getTypeAccount()));
                }
                break;
            case "Empresarial":
                if (accountDto.getTypeAccount().equals("Corriente")) {
                    Account account = new Account();
                    account.setIdClient(accountDto.getIdClient());
                    account.setTypeAccount(accountDto.getTypeAccount());
                    account.setNumberAccount(accountDto.getNumberAccount());
                    account.setBalance(accountDto.getBalance());
                    account.setLimit(accountDto.getLimit());
                    account.setDebt(accountDto.getDebt());
                    return accountRepository.save(account);
                } else {
                    return Mono.error(new Exception("CLIENTE EMPRESARIAL NO PUEDE TENER CUENTAS DE: " + accountDto.getTypeAccount()));

                }
            default:
                return null;
        }
            return null;
    }


	@Override
	public Mono<Account> saveTransaction(Transaction transaction) {
		
		Mono<Account> account = getAccount(transaction.getAccountDto());
		
		Account accot = account.block();
		if(accot.getTypeAccount().equals("Credito")) {
			
			 Double debt =accot.getDebt()+transaction.getAmount();
			if (debt>accot.getLimit()) {
				return account;
			}else {
				accot.setDebt(debt);
				return accountRepository.save(accot);
			}
		}else {
		 if(transaction.getOperation()==1) {
			 Double total= accot.getBalance()+transaction.getAmount();
			 accot.setBalance(total);
			 return accountRepository.save(accot);

		 }else {
			 Double total= accot.getBalance()-transaction.getAmount();
			 if (total<0) {
				 return account;
			 }else {
				 accot.setBalance(total);
				return  accountRepository.save(accot);
			 }
		 }
		 
		}
	
	}


	@Override
	public Mono<Account> updateAccount(Account account) {
        return accountRepository.save(account);
	}


	@Override
	public Mono<Account> getAccount(AccountDto accountDto) {
		return getAllByIdClient(accountDto.getIdClient()).filter(x -> x.getNumberAccount().equals(accountDto.getNumberAccount())).next();
	}


}