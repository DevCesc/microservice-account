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

	@Id
	private ObjectId id;
	//private int codTransaction;
	private String cardNumber;
	private String codClient;
	//private Date date;
	private String destAccount;
	private double monto;
	private Operation operation;

}
