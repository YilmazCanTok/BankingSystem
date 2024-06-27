package com.example.bankingsystem.service;

import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Transactional
    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> existingAccount = bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Account number already exists");
        }
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    @Override
    public BankAccount postTransaction(Long accountId, Transaction transaction) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.post(transaction);
            bankAccountRepository.save(bankAccount);
            if (transaction.getClass().getSimpleName().equals("WithdrawalTransaction")) {
                transaction.setPayee("Self");
            }
            return bankAccount;
        } else {
            throw new RuntimeException("Bank account not found");
        }
    }

    @Transactional
    @Override
    public BankAccount getBankAccountById(Long accountId) {
        return bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Bank account not found"));
    }

    @Transactional
    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }
}
