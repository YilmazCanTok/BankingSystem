package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.Transaction;

import java.util.List;

public interface BankAccountService {
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount postTransaction(Long accountId, Transaction transaction);
    BankAccount getBankAccountById(Long accountId);
    List<BankAccount> getAllBankAccounts();
}
