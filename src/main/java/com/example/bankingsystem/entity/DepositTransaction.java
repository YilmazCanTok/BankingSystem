package com.example.bankingsystem.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {
    public DepositTransaction(double amount) {
        super(amount, TransactionType.DEPOSIT);
    }

    public DepositTransaction() {
        super();
        setTransactionType(TransactionType.DEPOSIT);
    }

    @Override
    public String toString() {
        return "DepositTransaction{" +
                "amount=" + getAmount() +
                ", date=" + getDate() +
                '}';
    }
}
