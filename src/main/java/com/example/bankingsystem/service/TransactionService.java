package com.example.bankingsystem.service;

import com.example.bankingsystem.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getAllTransactions();
    List<TransactionDTO> getTransactionsByAccountId(Long accountId);
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransactionById(Long transactionId);
    void deleteTransaction(Long transactionId);
}
