package com.microservicio.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.microservicio.app.document.Account;
import com.microservicio.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
	public Mono<Account> updateByAccountCode(String accountcode,  Account account);
	public Mono<Account> deleteByAccountCode(String accountcode);	
	public Mono<Account> create (Account account);
	public Mono<Account> updateRetirement(String accountcode, Account account);
	public Mono<Account> updateDeposit(String accountcode, Account account);
	public Flux<Account> AllBalanceProduct(String documentnumber);
	public Mono<Account> getNumbereDepositByAccountcode( String accountcode);	
	public Mono<Account> getNumbereRetirementByAccountcode( String accountcode);
	public Flux<Account> findAll();
	public Mono<Account> findByAccountCode(String accountcode);
	public Mono<Account> updateinterbanktransaction(String accountcode,Account account);
}
 