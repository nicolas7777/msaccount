package com.microservicio.app.service;

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
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

import com.microservicio.app.config.ClientClient;
import com.microservicio.app.dao.AccountDao;
import com.microservicio.app.document.Account;
import com.microservicio.app.dto.BalancesummaryDto;
import com.microservicio.app.dto.ClientDto;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements IAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private ClientClient clientClient;

	@Autowired
	private AccountDao accountDao;

	@Override
	public Mono<Account> updateByAccountCode(String accountcode, Account account) {
		// LOGGER.info("AccountServiceImpl");
		return this.accountDao.findByAccountcode(accountcode)
				.map(p -> new Account(
						p.getId(),
						account.getIdclient(),
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

				).flatMap(this.accountDao::save);
	}

	@Override
	public Mono<Account> deleteByAccountCode(String accountcode) {
		// LOGGER.info("AccountServiceImpl");
		return this.accountDao.findByAccountcode(accountcode).map(p -> {
			p.setStatus("DELETED");
			return p;
		}).flatMap(this.accountDao::save);
	}

	@Override
	public Mono<Account> create(Account account) {
//		Mono<ClientDto> clientdto = this.clientClient.findByIdClientClient(account.getIdclient());
//		return clientdto.defaultIfEmpty(new ClientDto())
//				.flatMap(p->{					
//					if (p.getKindclient().equals("PERSONAL")) {					
//						System.out.println("Flux<Account> accountallclient "+this.accountDao.findByIdclient("2222222222222222222"));
//						//Flux<Account> accountallclient = this.accountRepository.findByIdclient(account.getIdclient());
//						//return accountallclient.defaultIfEmpty(new Account())
//						return this.accountDao.findByIdclient("2222222222222222222")
//								.flatMap(q->{	
//									System.out.println("****Flux<Account> accountallclient "+q.toString());
//									if(q.getKindaccount()==null) {
		// Permite el ingreso de data
		return this.accountDao.save(
				new Account(UUID.randomUUID().toString(),
					account.getIdclient(),
				account.getBankname(),
				account.getKindaccount(), 
				"ACC" + UUID.randomUUID().toString(), 0.0, "CREATED",
				new Date(), account.getPercent() == null ? 0.5 : account.getPercent(),
				account.getNumberdeposit() == 0 ? 2 : account.getNumberdeposit(),
				account.getNumberretirement() == 0 ? 2 : account.getNumberretirement(),
				account.getLimit() == null ? 1200.0 : account.getLimit(),
				account.getCommission() == null ? 0.1 : account.getCommission()));
		// Fin
//									}
//									else {										
//										return null;
//									}									
//								}).then(Mono.just(null));
//						
//					}
//					if (p.getKindclient()=="BUSINESS") {
//						System.out.println("El ususario es BUSINESS");
//						if (account.getKindaccount()=="savings account" || account.getKindaccount()=="savings account"  )
//						{
//							return null;
//						}
//						else
//							//Permite el ingreso de data
//							return this.accountDao.save(new Account(
//									UUID.randomUUID().toString(),
//									account.getIdclient(),
//									account.getBankname(),
//									account.getKindaccount(),
//									"ACC"+UUID.randomUUID().toString(),	
//									0.0,
//									"CREATED",
//									new Date(),
//									account.getPercent()==null?0.5:account.getPercent(),
//									account.getNumberdeposit()==0? 2:account.getNumberdeposit(),
//									account.getNumberretirement()==0? 2:account.getNumberretirement(),
//									account.getLimit()==null?1200.0:account.getLimit(),
//									account.getCommission()==null?0.1:account.getCommission()
//									));	
//							//Fin						
//					}
//					else {
//						return null;
//						
//					}				
//				});

	}

	@Override
	public Mono<Account> updateRetirement(String accountcode, Account account) {
		// LOGGER.info("AccountServiceImpl");
		return this.accountDao.findByAccountcode(accountcode).map(q -> {
			q.setNumberretirement(q.getNumberretirement() - 1);
			q.setAmount(account.getAmount());
			q.setDate(new Date());
			return q;

		}).flatMap(this.accountDao::save);
	}

	@Override
	public Mono<Account> updateDeposit(String accountcode, Account account) {
		// LOGGER.info("AccountServiceImpl");
		return this.accountDao.findByAccountcode(accountcode).map(q -> {
			q.setNumberdeposit(q.getNumberdeposit() - 1);
			q.setAmount(account.getAmount());
			q.setDate(new Date());
			return q;

		}).flatMap(this.accountDao::save);
	}

	public Flux<Account> AllBalanceProduct(String documentnumber) {

		return this.clientClient.getClientByDocumentNumber(documentnumber).flatMapMany(p -> {
			return this.accountDao.findByIdclient(p.getId());
		});
	}

	@Override
	public Mono<Account> getNumbereDepositByAccountcode(String accountcode) {
		return this.accountDao.findByAccountcode(accountcode);
	}

	@Override
	public Mono<Account> getNumbereRetirementByAccountcode(String accountcode) {
		return this.accountDao.findByAccountcode(accountcode);
	}

	@Override
	public Flux<Account> findAll() {
		return this.accountDao.findAll();
	}

	@Override
	public Mono<Account> findByAccountCode(String accountcode) {
		return this.accountDao.findByAccountcode(accountcode);
	}

	@Override
	public Mono<Account> updateinterbanktransaction(String accountcode, Account account) {
		// LOGGER.info("AccountServiceImpl");
		return this.accountDao.findByAccountcode(accountcode).map(q -> {
			q.setNumberretirement(q.getNumberretirement());
			q.setAmount(account.getAmount());
			q.setDate(new Date());
			return q;
		}).flatMap(this.accountDao::save);
	}

	@Override
	public Mono<ClientDto> findByIdClientClient(String idclient) {
		return this.clientClient.findByIdClientClient(idclient);
	}

	@Override
	public Mono<Account> createcollection(Account account) {
		// Permite el ingreso de data
		return this.accountDao.save(new Account( UUID.randomUUID().toString(),account.getIdclient(),
				account.getBankname(), account.getKindaccount(), "ACC" + UUID.randomUUID().toString(), 0.0, "CREATED",
				new Date(), account.getPercent() == null ? 0.5 : account.getPercent(),
				account.getNumberdeposit() == 0 ? 2 : account.getNumberdeposit(),
				account.getNumberretirement() == 0 ? 2 : account.getNumberretirement(),
				account.getLimit() == null ? 1200.0 : account.getLimit(),
				account.getCommission() == null ? 0.1 : account.getCommission()));
		// Fin
	}

	@Override
	public Mono<List<BalancesummaryDto>> balancesummary(String documentnumber) {
		List<BalancesummaryDto> lBalancesumaryDto = new ArrayList<BalancesummaryDto>();
		return this.clientClient.getClientByDocumentNumber(documentnumber).flatMapMany(p -> {
			return this.accountDao.findByIdclient(p.getId());
		})
		.map(q -> {
			if (q.getKindaccount().equals("PERSONAL") || q.getKindaccount().equals("BUSINESS")
					|| q.getKindaccount().equals("CREADIT CARDS") || q.getKindaccount().equals("CASH ADVANCE"))
				lBalancesumaryDto.add(new BalancesummaryDto(q.getBankname(),q.getKindaccount(),q.getAccountcode(), (q.getLimit()-q.getAmount())));
			return lBalancesumaryDto;
		}).then(Mono.just(lBalancesumaryDto));

	}
}
