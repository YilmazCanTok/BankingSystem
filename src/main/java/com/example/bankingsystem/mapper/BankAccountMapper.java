package com.example.bankingsystem.mapper;

import com.example.bankingsystem.dto.BankAccountDTO;
import com.example.bankingsystem.dto.TransactionDTO;
import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.DepositTransaction;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.WithdrawalTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccountDTO toDto(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setOwner(bankAccount.getOwner());
        bankAccountDTO.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setPhoneNumber(bankAccount.getPhoneNumber());
        if (bankAccount.getTransactions() != null) {
            bankAccountDTO.setTransactions(
                    bankAccount.getTransactions().stream()
                            .map(this::toTransactionDto)
                            .collect(Collectors.toList())
            );
        }
        return bankAccountDTO;
    }

    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountDTO.getId());
        bankAccount.setOwner(bankAccountDTO.getOwner());
        bankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setPhoneNumber(bankAccountDTO.getPhoneNumber());
        if (bankAccountDTO.getTransactions() != null) {
            bankAccount.setTransactions(
                    bankAccountDTO.getTransactions().stream()
                            .map(this::toTransactionEntity)
                            .collect(Collectors.toList())
            );
        }
        return bankAccount;
    }

    private TransactionDTO toTransactionDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setTransactionType(transaction.getClass().getSimpleName());
        transactionDTO.setPayee(transaction.getPayee());
        return transactionDTO;
    }

    private Transaction toTransactionEntity(TransactionDTO transactionDTO) {
        Transaction transaction;
        if ("DepositTransaction".equals(transactionDTO.getTransactionType())) {
            transaction = new DepositTransaction(transactionDTO.getAmount());
        } else if ("WithdrawalTransaction".equals(transactionDTO.getTransactionType())) {
            transaction = new WithdrawalTransaction(transactionDTO.getAmount());
        } else {
            throw new IllegalArgumentException("Unsupported transaction type");
        }
        transaction.setId(transactionDTO.getId());
        transaction.setDate(transactionDTO.getDate() != null ? transactionDTO.getDate() : LocalDateTime.now());
        transaction.setPayee(transactionDTO.getPayee());
        return transaction;
    }
}
