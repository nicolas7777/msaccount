package com.microservicio.app;

import java.util.Date;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.microservicio.app.document.Account;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsaccountApplicationTests {	
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Test
	void contextLoads() {
	}
	
	@Autowired
	private WebTestClient webTestClient;
	
	//CRUD:save
		@Test
		void saveBank() {
			//mongoTemplate.dropCollection("account").subscribe();
			//String sUUID = UUID.randomUUID().toString();
			Account account = new Account(
					UUID.randomUUID().toString(),
					"2222222222222222222",//idcliente
				    "INTERBANK",
				    "AHORROS",
				    "",//accountcode
				     0.0, //amount
				    "",//status
				    null,//date
				    0.0,//percent
				    2,//deposit
				    3,//retirement,
				    1500.0, //limit
				    0.8 //commission
		);
			webTestClient.post()
			.uri("/account/createcollection")
			.body(Mono.just(account), Account.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Account.class)
			.consumeWith(response -> {
				Account accountres = response.getResponseBody();
				System.out.println("[Cliente registrado] " + account);
				//Assertions.assertThat(clientres.getId()).isNotNull().isEqualTo(sUUID);			
				Assertions.assertThat(accountres.getBankname()).isNotNull().isEqualTo("INTERBANK");
				Assertions.assertThat(accountres.getKindaccount()).isNotNull().isEqualTo("AHORROS");
				Assertions.assertThat(accountres.getAmount()).isNotNull().isEqualTo(0.0);
				Assertions.assertThat(accountres.getStatus()).isNotNull().isEqualTo("CREATED");
				Assertions.assertThat(accountres.getPercent()).isNotNull().isEqualTo(0.0);
				Assertions.assertThat(accountres.getNumberdeposit()).isNotNull().isEqualTo(2);
				Assertions.assertThat(accountres.getNumberretirement()).isNotNull().isEqualTo(3);
				Assertions.assertThat(accountres.getLimit()).isNotNull().isEqualTo(1500.0);
				Assertions.assertThat(accountres.getCommission()).isNotNull().isEqualTo(0.8);
			});
				
			  
		}

}
