package com.microservicio.app.dto;

import java.util.Date;

import com.microservicio.app.document.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalancesummaryDto {
	private String bankname;
	private String kindaccount;
	private String accountcode;
	private Double amount;

}
