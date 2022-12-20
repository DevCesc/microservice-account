package com.bootcamp.entity;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "card")
public class Card {

	@Id
	private ObjectId id;
	private ObjectId idClient;
	private String  cardNumber;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer cvv2;
	private Account account;
	
}
