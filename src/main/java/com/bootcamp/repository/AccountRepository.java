package com.bootcamp.repository;

import com.bootcamp.entity.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, ObjectId> {

    Flux<Account> findAllByIdClient(ObjectId id);

    Mono<Account> findByIdClientAndTypeAccount(String id, String typeAccount);

}
