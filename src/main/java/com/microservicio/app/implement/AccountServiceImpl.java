package com.microservicio.app.implement;

import java.net.URI;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.app.client.ClientClient;
import com.microservicio.app.document.Account;
import com.microservicio.app.dto.ClientDto;
import com.microservicio.app.repository.AccountRepository;
import com.microservicio.app.service.IAccountService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements IAccountService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	
	@Autowired
	private ClientClient clientClient;
	
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Mono<Account> updateByAccountCode(String accountcode,  Account account) {
		//LOGGER.info("AccountServiceImpl");		
			return this.accountRepository
					.findByAccountcode(accountcode)
					.map(p->
						new Account (
								p.getId(),
								account.getIdcliente(),
								account.getBankname(),
								account.getKindaccount(),
								p.getAccountcode(),		
								account.getAmount(),
								account.getStatus(),
								new Date(),
								account.getPercent(),
								account.getNumberdeposit(),
								account.getNumberretirement(),
								account.getLimit(),
								account.getCommission()
								
								)
												
					)
					.flatMap(this.accountRepository::save);
					
	}

	@Override
	public Mono<Account> deleteByAccountCode(String accountcode) {
		//LOGGER.info("AccountServiceImpl");
		return this.accountRepository
				.findByAccountcode(accountcode)
				.map(p->{
					p.setStatus("DELETED");
					return p;						
				})
				.flatMap(this.accountRepository::save);		
	}

	@Override
	public Mono<Account> create (Account account) {
		return this.accountRepository.save(new Account(
				UUID.randomUUID().toString(),
				account.getIdcliente(),
				account.getBankname(),
				account.getKindaccount(),
				"ACC"+UUID.randomUUID().toString(),	
				0.0,
				"CREATED",
				new Date(),
				account.getPercent(),
				account.getNumberdeposit(),
				account.getNumberretirement(),
				account.getLimit(),
				account.getCommission()
				));	 
	}

	@Override
	public Mono<Account> updateRetirement(String accountcode,Account account) {
		//LOGGER.info("AccountServiceImpl");
		return this.accountRepository.findByAccountcode(accountcode)
		.map(q->{
				q.setNumberretirement(q.getNumberretirement()-1);
				q.setAmount(account.getAmount());
				q.setDate(new Date());
				return q;	
						
		})		
		.flatMap(this.accountRepository::save);
	}
	
	@Override
	public Mono<Account> updateDeposit(String accountcode,Account account) {
			//LOGGER.info("AccountServiceImpl");
			return this.accountRepository.findByAccountcode(accountcode)
			.map(q->{
						q.setNumberdeposit(q.getNumberdeposit()-1);
							q.setAmount(account.getAmount());							
							q.setDate(new Date());
							return q;
							
				}
			)			
			.flatMap(this.accountRepository::save);
		}
	
	public Flux<Account> AllBalanceProduct(String documentnumber){
		
				return this.clientClient.getClientByDocumentNumber(documentnumber)
				.flatMap(p ->{
					return this.accountRepository.AllByIdClient(p.getIdcliente());
					
				});				
	}

	@Override
	public Mono<Account> getNumbereDepositByAccountcode(String accountcode) {
		return this.accountRepository.findByAccountcode(accountcode);
	}
	
	@Override
	public Mono<Account> getNumbereRetirementByAccountcode(String accountcode) {
		return this.accountRepository.findByAccountcode(accountcode);
	}
	
	@Override
	public Flux<Account> findAll() {
		return this.accountRepository.findAll();
	}

	@Override
	public Mono<Account> findByAccountCode(String accountcode) {
		return this.accountRepository.findByAccountcode(accountcode);
	}
	
	@Override
	public Mono<Account> updateinterbanktransaction(String accountcode,Account account) {
		//LOGGER.info("AccountServiceImpl");
		return this.accountRepository.findByAccountcode(accountcode)
		.map(q->{
				q.setNumberretirement(q.getNumberretirement());
				q.setAmount(account.getAmount());
				q.setDate(new Date());
				return q;						
		})		
		.flatMap(this.accountRepository::save);
	}
}




