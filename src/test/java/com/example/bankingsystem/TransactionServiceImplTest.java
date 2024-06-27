package com.example.bankingsystem;

import com.example.bankingsystem.dto.TransactionDTO;
import com.example.bankingsystem.entity.BankAccount;
import com.example.bankingsystem.entity.DepositTransaction;
import com.example.bankingsystem.entity.Transaction;
import com.example.bankingsystem.entity.WithdrawalTransaction;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.mapper.TransactionMapper;
import com.example.bankingsystem.repository.BankAccountRepository;
import com.example.bankingsystem.repository.TransactionRepository;
import com.example.bankingsystem.service.TransactionServiceImpl;
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

public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private BankAccount bankAccount;
    private TransactionDTO transactionDTO;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setOwner("John Doe");
        bankAccount.setAccountNumber("123456");
        bankAccount.setBalance(1000.0);
        bankAccount.setPhoneNumber("1234567890");

        transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(200.0);
        transactionDTO.setPayee("Self");
        transactionDTO.setTransactionType("DepositTransaction");

        transaction = new DepositTransaction(200.0);
        transaction.setId(1L);
        transaction.setPayee("Self");
        transaction.setBankAccount(bankAccount);
    }

    @Test
    public void testGetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);
        assertEquals(1, transactionService.getAllTransactions().size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void testGetTransactionsByAccountId() {
        when(transactionRepository.findByBankAccountId(anyLong())).thenReturn(List.of(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);
        assertEquals(1, transactionService.getTransactionsByAccountId(1L).size());
        verify(transactionRepository, times(1)).findByBankAccountId(1L);
    }

    @Test
    public void testCreateTransaction() {
        when(transactionMapper.toEntity(any(TransactionDTO.class))).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        assertNotNull(createdTransaction);
        assertEquals(transactionDTO.getAmount(), createdTransaction.getAmount());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void testGetTransactionById() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDTO);
        TransactionDTO foundTransaction = transactionService.getTransactionById(1L);
        assertNotNull(foundTransaction);
        assertEquals(transactionDTO.getAmount(), foundTransaction.getAmount());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteTransaction() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        doNothing().when(transactionRepository).delete(any(Transaction.class));

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }
}
