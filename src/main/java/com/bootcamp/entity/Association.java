package com.bootcamp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(value = "association")
public class Association {
	
	@Id
	private ObjectId id;
	private ObjectId idYanki;
	private String cardNumber;
	private int expirationMonth;
	private int expirationYear;
	private int cvv2;
	private String name;
	private String lastName;

}
