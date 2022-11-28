package com.bootcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private ObjectId id;
    private ObjectId idClient;
    private String typeAccount;
    private String numberAccount;
    private String typeClient;



}
