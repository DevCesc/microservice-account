package com.bootcamp.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
	
	  private ObjectId idClient;
	  private String  numberAccount;
	  private Double moneyPay;
	  private AccountDto accountDto;
	  private String numberCredit;
	  private double amount;
	  
}
