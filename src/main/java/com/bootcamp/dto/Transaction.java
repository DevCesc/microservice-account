package com.bootcamp.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction  {

	private AccountDto accountDto;
	private String message;
	private Double amount;
	private int operation;
	private String sourceAccount; 
	
	
}
