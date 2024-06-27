package com.example.bankingsystem.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    private String payee;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", insertable = false, updatable = false)
    private TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType) {
        this.date = LocalDateTime.now();
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    @Override
    public abstract String toString();
}
