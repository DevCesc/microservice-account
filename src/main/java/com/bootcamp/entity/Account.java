package com.bootcamp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
@ToString
public class Account {

    @Id
    private ObjectId id;
    private ObjectId idClient;
    private String typeAccount;
    private String numberAccount;
    private Double balance;
    private Double debt;
    private Double limit;
    private int numMaxTrans;
    
    
}
