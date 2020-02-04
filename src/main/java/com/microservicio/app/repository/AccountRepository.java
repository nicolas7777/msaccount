package com.microservicio.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservicio.app.document.Account;
import com.microservicio.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account,String> {

	//@Query(value = "{'accountcode' : ?0}")
	public Mono<Account> findByAccountcode(String accountcode);
	
	@Query(value = "{'idclient' : ?0}")
	public Flux<Account> AllByIdClient(String idClient);

}



