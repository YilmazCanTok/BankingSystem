package com.example.bankingsystem.mapper;

import com.example.bankingsystem.dto.TransactionDTO;
import com.example.bankingsystem.entity.DepositTransaction;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.WithdrawalTransaction;
import com.example.bankingsystem.entity.BillPaymentTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public TransactionDTO toDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setTransactionType(transaction.getClass().getSimpleName());
        transactionDTO.setPayee(transaction.getPayee());
        return transactionDTO;
    }

    public Transaction toEntity(TransactionDTO transactionDTO) {
        Transaction transaction;
        switch (transactionDTO.getTransactionType()) {
            case "DEPOSIT":
                transaction = new DepositTransaction(transactionDTO.getAmount());
                break;
            case "WITHDRAWAL":
                transaction = new WithdrawalTransaction(transactionDTO.getAmount());
                break;
            case "BILL_PAYMENT":
                transaction = new BillPaymentTransaction(transactionDTO.getAmount(), transactionDTO.getPayee());
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type");
        }
        transaction.setId(transactionDTO.getId());
        transaction.setDate(transactionDTO.getDate() != null ? transactionDTO.getDate() : LocalDateTime.now());
        transaction.setPayee(transactionDTO.getPayee());
        return transaction;
    }
}
