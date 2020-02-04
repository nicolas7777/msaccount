package com.microservicio.app.controller;

import java.net.URI;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.document.Account;
import com.microservicio.app.dto.ClientDto;
import com.microservicio.app.service.IAccountService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/account")
public class AccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	//inyeccion de dependencias
	@Autowired
	private IAccountService accountService;
	

	@PostMapping("/create")
	@ApiOperation(value = "CRUD", notes="")
	Mono<Account> create(@RequestBody Account account){
		//LOGGER.info("AccountController");
		return this.accountService.create(account);
	}


	@DeleteMapping("/deletebyaccountcode/{accountcode}")
	@ApiOperation(value = "CRUD", notes="")	
	Mono<Account>deleteByAccountCode(@PathVariable String accountcode){
		//LOGGER.info("AccountController");
		return this.accountService.deleteByAccountCode(accountcode);
	}
	
	@PutMapping("/updatebyaccountcode/{accountcode}")
	@ApiOperation(value = "CRUD", notes="")	
	Mono<Account> updateByAccountCode(@PathVariable String accountcode,@RequestBody Account account){
		//LOGGER.info("AccountController");
		return this.accountService.updateByAccountCode(accountcode,account);
	}
	
	@PutMapping("/updatedeposit/{accountcode}")
	@ApiOperation(value = "UPDATE DEPOSIT *.*", notes="")	
	Mono<Account> updateDeposit(@PathVariable String accountcode, @RequestBody Account account){
		//LOGGER.info("AccountController");
		return this.accountService.updateDeposit(accountcode,account);
	}
	
	@PutMapping("/updateretirement/{accountcode}")
	@ApiOperation(value = "UPDATE RETIREMENT *.*", notes="")	
	Mono<Account> updateRetirement(@PathVariable String accountcode, @RequestBody Account account){
		//LOGGER.info("AccountController");
		return this.accountService.updateRetirement(accountcode,account);
	}	
	
	@GetMapping("/allbalanceproduct/{documentnumber}")
	@ApiOperation(value = "BALANCE PRODUCT", notes="")	
	Flux<Account> AllBalanceProduct(@PathVariable String documentnumber){
		//LOGGER.info("TransactionController");
		return this.accountService.AllBalanceProduct(documentnumber);
	}
	
	@GetMapping("/getnumberedepositbyaccountcode/{accountcode}")
	@ApiOperation(value = "*.*", notes="")	
	Mono<Account> getNumbereDepositByAccountcode(@PathVariable String accountcode){
		//LOGGER.info("TransactionController");
		return this.accountService.getNumbereDepositByAccountcode(accountcode);
	}
	
	@GetMapping("/getNumbereRetirementByAccountcode/{accountcode}")
	@ApiOperation(value = "*.*", notes="")	
	Mono<Account> getNumbereRetirementByAccountcode(@PathVariable String accountcode){
		//LOGGER.info("TransactionController");
		return this.accountService.getNumbereRetirementByAccountcode(accountcode);
	}
	
	@GetMapping("/findall")
	@ApiOperation(value = "findAll", notes="")	
	Flux<Account> findall(){
		//LOGGER.info("TransactionController");
		return this.accountService.findAll();
	}
	
	@GetMapping("/findbyaccountcode/{accountcode}")
	@ApiOperation(value = "findByAccountCode for interbank transaction", notes="")	
	Mono<Account> findByAccountCode(@PathVariable String accountcode){
		//LOGGER.info("TransactionController");
		return this.accountService.findByAccountCode(accountcode);
	}	
	
	@PutMapping("/updateinterbanktransaction/{accountcode}")
	@ApiOperation(value = "update interbank transaction", notes="")	
	Mono<Account> updateinterbanktransaction(@PathVariable String accountcode, @RequestBody Account account){
		//LOGGER.info("AccountController");
		return this.accountService.updateinterbanktransaction(accountcode,account);
	}
	
	
}
