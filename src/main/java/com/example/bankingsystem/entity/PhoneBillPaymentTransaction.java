package com.example.bankingsystem.entity;

import lombok.Data;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("PHONE_BILL_PAYMENT")
public class PhoneBillPaymentTransaction extends BillPaymentTransaction {
    private String phoneNumber;

    public PhoneBillPaymentTransaction(double amount, String payee, String phoneNumber) {
        super(amount, payee);
        this.phoneNumber = phoneNumber;
    }

    public PhoneBillPaymentTransaction() {
        super();
    }

    @Override
    public String toString() {
        return "PhoneBillPaymentTransaction{" +
                "amount=" + getAmount() +
                ", date=" + getDate() +
                ", payee='" + getPayee() + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
