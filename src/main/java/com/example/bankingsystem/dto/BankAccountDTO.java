package com.example.bankingsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankAccountDTO {
    private Long id;
    private String owner;
    private String accountNumber;
    private double balance;
    private String phoneNumber;
    private List<TransactionDTO> transactions;
}
