package com.example.bankingsystem.service;

import com.example.bankingsystem.dto.TransactionDTO;
import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.DepositTransaction;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.WithdrawalTransaction;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.mapper.TransactionMapper;
import com.example.bankingsystem.repository.TransactionRepository;
import com.example.bankingsystem.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByBankAccountId(accountId).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + transactionId));
        return transactionMapper.toDto(transaction);
    }

    @Transactional
    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + transactionId));

        BankAccount bankAccount = transaction.getBankAccount();
        if (transaction instanceof DepositTransaction) {
            bankAccount.debit(transaction.getAmount());
        } else if (transaction instanceof WithdrawalTransaction) {
            bankAccount.credit(transaction.getAmount());
        }
        bankAccountRepository.save(bankAccount);

        transactionRepository.delete(transaction);
    }
}
