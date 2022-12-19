package com.bootcamp.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRest {


	
	private ObjectId id;
	//private int codTransaction;
	private String codClient;
	//private Date date;
	private String sourceAccount;
	private String destinationAccount;
	private double amount;
	private Operation operation;
	private Double commission;

}
