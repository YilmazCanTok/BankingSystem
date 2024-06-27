package com.example.bankingsystem.entity;

import lombok.Data;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("BILL_PAYMENT")
public class BillPaymentTransaction extends Transaction {
    private String payee;

    public BillPaymentTransaction(double amount, String payee) {
        super(amount, TransactionType.BILL_PAYMENT);
        this.payee = payee;
    }

    public BillPaymentTransaction() {
        super();
        setTransactionType(TransactionType.BILL_PAYMENT);
    }

    @Override
    public String toString() {
        return "BillPaymentTransaction{" +
                "amount=" + getAmount() +
                ", date=" + getDate() +
                ", payee='" + payee + '\'' +
                '}';
    }
}
