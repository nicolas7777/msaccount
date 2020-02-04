package com.microservicio.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.microservicio.app.document.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDto {
	private String id;
	private String firstname;
	private String lastname;
	private String kindclient;
	private String documentnumber;	
	private Date date;
	private String status;
	public Account account;
}
