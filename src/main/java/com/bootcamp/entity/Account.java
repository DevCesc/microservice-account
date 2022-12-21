package com.bootcamp.entity;

import lombok.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
@ToString
@Builder
public class Account {

    @Id
    private ObjectId id;
    private ObjectId idClient;
    private String typeAccount;
    private String numberAccount;
    private String numberAccountInterbank;
    private String idYanki;
    private String classification;
    private boolean walletYanki;
    private String status;
    private Double balance;
    private Double debt;
    private Double limit;
    private int numMaxTrans;
    
    
}
