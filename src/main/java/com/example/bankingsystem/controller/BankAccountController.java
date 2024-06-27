package com.example.bankingsystem.controller;

import com.example.bankingsystem.dto.BankAccountDTO;
import com.example.bankingsystem.dto.TransactionDTO;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.TransactionType;
import com.example.bankingsystem.mapper.BankAccountMapper;
import com.example.bankingsystem.mapper.TransactionMapper;
import com.example.bankingsystem.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping
    public BankAccountDTO createBankAccount(@RequestParam String owner, @RequestParam String accountNumber, @RequestParam String phoneNumber, @RequestParam double balance) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setOwner(owner);
        bankAccountDTO.setAccountNumber(accountNumber);
        bankAccountDTO.setPhoneNumber(phoneNumber);
        bankAccountDTO.setBalance(balance);
        return bankAccountMapper.toDto(bankAccountService.createBankAccount(bankAccountMapper.toEntity(bankAccountDTO)));
    }

    @PostMapping("/{accountId}/withdrawOrDeposit")
    public BankAccountDTO withdrawOrDeposit(@PathVariable Long accountId, @RequestParam double amount, @RequestParam String payee, @RequestParam TransactionType transactionType) {
        BankAccountDTO bankAccountDTO = bankAccountMapper.toDto(bankAccountService.getBankAccountById(accountId));
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setTransactionType(transactionType.name());
        transactionDTO.setDate(LocalDateTime.now());
        transactionDTO.setPayee(payee);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setBankAccount(bankAccountService.getBankAccountById(accountId));
        BankAccountDTO updatedBankAccountDTO = bankAccountMapper.toDto(bankAccountService.postTransaction(accountId, transaction));
        if (transactionType == TransactionType.WITHDRAWAL) { // Withdrawal
            transaction.setPayee("Self");
        }
        return updatedBankAccountDTO;
    }

    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccountById(@PathVariable Long accountId) {
        return bankAccountMapper.toDto(bankAccountService.getBankAccountById(accountId));
    }

    @GetMapping
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts().stream()
                .map(bankAccountMapper::toDto)
                .collect(Collectors.toList());
    }
}
