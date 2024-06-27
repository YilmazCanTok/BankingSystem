package com.example.bankingsystem.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Owner cannot be null")
    private String owner;

    @Column(unique = true)
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    private double balance;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>(); // Transactions listesini başlat

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void post(Transaction transaction) {
        transactions.add(transaction);
        transaction.setBankAccount(this);
        if (transaction instanceof DepositTransaction) {
            credit(transaction.getAmount());
        } else if (transaction instanceof WithdrawalTransaction || transaction instanceof BillPaymentTransaction) {
            debit(transaction.getAmount());
        }
    }
}
