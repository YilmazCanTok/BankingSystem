package com.example.bankingsystem.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WITHDRAWAL")
public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(double amount) {
        super(amount, TransactionType.WITHDRAWAL);
    }

    public WithdrawalTransaction() {
        super();
        setTransactionType(TransactionType.WITHDRAWAL);
    }

    @Override
    public String toString() {
        return "WithdrawalTransaction{" +
                "amount=" + getAmount() +
                ", date=" + getDate() +
                '}';
    }
}
