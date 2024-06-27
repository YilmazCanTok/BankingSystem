package com.example.bankingsystem;

import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.DepositTransaction;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.WithdrawalTransaction;
import com.example.bankingsystem.repository.BankAccountRepository;
import com.example.bankingsystem.service.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setOwner("John Doe");
        bankAccount.setAccountNumber("123456");
        bankAccount.setBalance(1000.0);
        bankAccount.setPhoneNumber("1234567890");
    }

    @Test
    public void testCreateBankAccount() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        BankAccount createdBankAccount = bankAccountService.createBankAccount(bankAccount);
        assertNotNull(createdBankAccount);
        assertEquals(bankAccount.getOwner(), createdBankAccount.getOwner());
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    public void testPostTransaction() {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        Transaction depositTransaction = new DepositTransaction(500.0);
        bankAccountService.postTransaction(bankAccount.getId(), depositTransaction);

        assertEquals(1500.0, bankAccount.getBalance());
        verify(bankAccountRepository, times(1)).save(bankAccount);

        Transaction withdrawalTransaction = new WithdrawalTransaction(200.0);
        bankAccountService.postTransaction(bankAccount.getId(), withdrawalTransaction);

        assertEquals(1300.0, bankAccount.getBalance());
        verify(bankAccountRepository, times(2)).save(bankAccount);
    }

    @Test
    public void testGetBankAccountById() {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));
        BankAccount foundBankAccount = bankAccountService.getBankAccountById(1L);
        assertNotNull(foundBankAccount);
        assertEquals(bankAccount.getOwner(), foundBankAccount.getOwner());
        verify(bankAccountRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllBankAccounts() {
        when(bankAccountRepository.findAll()).thenReturn(List.of(bankAccount));
        assertEquals(1, bankAccountService.getAllBankAccounts().size());
        verify(bankAccountRepository, times(1)).findAll();
    }
}
