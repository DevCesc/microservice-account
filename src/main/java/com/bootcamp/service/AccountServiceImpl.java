package com.bootcamp.service;

import com.bootcamp.clients.TransactionClientRest;
import com.bootcamp.dto.AccountDto;
import com.bootcamp.dto.Credit;
//import com.bootcamp.dto.Credit;
import com.bootcamp.dto.Transaction;
import com.bootcamp.dto.TransactionRest;
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
	private TransactionClientRest clientRest;
	

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Flux<Account> getAllByIdClient(ObjectId id) {

        Flux<Account> accountFlux = accountRepository.findAllByIdClient(id);
        return accountFlux;
    }


    @Override
    public Mono<Account> saveTypeClient(AccountDto accountDto) {

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
                    account.setNumMaxTrans(0);
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
                    account.setNumMaxTrans(0);
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
    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> findByIdClientAndtypeAccount(String id, String typeAccount) {
        return accountRepository.findByIdClientAndTypeAccount(id,typeAccount);
    }
    
    public Mono<Account> saveTransactionCredit(AccountDto numberAccount, Double amount){
		Mono<Account> account = getAccount(numberAccount);
		Account accot = account.block();
		System.out.println("saveTransactionCredit: "+accot.getDebt());
		Double debt =accot.getDebt()+amount;
		if (debt>accot.getLimit()) {
			return account;
		}else {
			accot.setDebt(debt);
			return accountRepository.save(accot);
		}
    }
    
    
    public Mono<Account> saveTransactionRetirement(AccountDto numberAccount, Double amount){
		Mono<Account> account = getAccount(numberAccount);
		Account accot = account.block();
		 Double total= accot.getBalance()-amount;
		 if (total<0) {
			 return account;
		 }else {
			 //Inicio - Cobro de comisiones 
			 if(accot.getNumMaxTrans()>=5) {
				 total= accot.getBalance()-(amount-0.10); 
			 }
			 //Fin - Cobro de comisiones 
			 accot.setBalance(total);
			 accot.setNumMaxTrans(accot.getNumMaxTrans()+1);
			return  accountRepository.save(accot);
        }
    }

    @Override
	public Mono<Account> saveTransaction(Transaction transaction) {
		
		Mono<Account> account = getAccount(transaction.getAccountDto());
		
		Account accot = account.block();
		if(accot.getTypeAccount().equals("Credito")) {
			
			//Double debt =accot.getDebt()+transaction.getAmount();
			//if (debt>accot.getLimit()) {
			//	return account;
			//}else {
			//	accot.setDebt(debt);
			//	return accountRepository.save(accot);
			//}
			return saveTransactionCredit(transaction.getAccountDto(),transaction.getAmount());
		}else {
		 if(transaction.getOperation()==1) {
			 //Deposito
			 System.out.println("Entro 1");
			 Double total= accot.getBalance()+transaction.getAmount();
			 //Inicio - Cobro de comisiones 
			 if(accot.getNumMaxTrans()>=5) {
				  total= accot.getBalance()+(transaction.getAmount()-0.10);
			 }
			 //Fin - Cobro de comisiones 
			 accot.setBalance(total);
			 accot.setNumMaxTrans(accot.getNumMaxTrans()+1);
			//TransactionRest objRest = new TransactionRest();
		    //bjRest.setCardNumber(accot.getNumberAccount());
			// clientRest.save(objRest);
			 return accountRepository.save(accot);

		 }else if (transaction.getOperation()==2) {
			//Retiro
			/* System.out.println("Entro 2");
			 Double total= accot.getBalance()-transaction.getAmount();
			 if (total<0) {
				 return account;
			 }else {
				 //Inicio - Cobro de comisiones 
				 if(accot.getNumMaxTrans()>=5) {
					 total= accot.getBalance()-(transaction.getAmount()-0.10); 
				 }
				 //Fin - Cobro de comisiones 
				 accot.setBalance(total);
				 accot.setNumMaxTrans(accot.getNumMaxTrans()+1);
				return  accountRepository.save(accot);
			 }*/
		   return saveTransactionRetirement(transaction.getAccountDto(),transaction.getAmount());
		 }else {
			 //Transferencia
			 System.out.println("Entro 3");
			 Double total= accot.getBalance()-transaction.getAmount();
			 if (total<0) {
				 return account;
			 }else {
				 //Inicio - Cobro de comisiones 
				 if(accot.getNumMaxTrans()>=5) {
					 System.out.println("Entro comsinoes");
					 total= accot.getBalance()-(transaction.getAmount()-0.10); 
				 }
				 System.out.println("Saldo actual:" +total );
				 //Fin - Cobro de comisiones 
				 accot.setBalance(total);
				 accot.setNumMaxTrans(accot.getNumMaxTrans()+1);

				 //Inicio tranferencia
				 Mono<Account> sourceaMono = getAccountbyNumAccount(transaction.getSourceAccount());
				 Account sourceaccot = sourceaMono.block();
				 Double sourcetotal= sourceaccot.getBalance()+transaction.getAmount();
				 sourceaccot.setBalance(sourcetotal);
				 //Fin transferencia
				 return accountRepository.save(accot).mergeWith(accountRepository.save(sourceaccot)).next();					 
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


	@Override
	public Mono<Account> getBalanceByAccount(ObjectId idCli, String numberAccount) {
		return getAllByIdClient(idCli).filter(x -> x.getNumberAccount().equals(numberAccount)).next();
	}




	@Override
	public Mono<Account> getAccountbyNumAccount(String numaccountDto) {
		return accountRepository.findAll().filter(x -> x.getNumberAccount().equals(numaccountDto)).next();
	}


	@Override
	public Mono<Account> payCredicAccount(Credit  credit) {
		
		Mono<Account> account = getBalanceByAccount(credit.getIdClient(),credit.getNumberAccount());
		Account accot = account.block();
		System.out.println("lo que debe: "+accot.getDebt());
		accot.setDebt(accot.getDebt()-credit.getMoneyPay());
		return accountRepository.save(accot).mergeWith(saveTransactionRetirement(credit.getAccountDto(),credit.getMoneyPay())).next();
	}
	
//	@Override
//	public Mono<Account> payCredicAccount(Credit  credit) {
//		Mono<Account> account = getBalanceByAccount(credit.getIdClient(),credit.getNumberAccount());
//		Account accot = account.block();
//		accot.setDebt(accot.getDebt()-credit.getMoneyPay());
//		return accountRepository.save(accot);
//	}


}