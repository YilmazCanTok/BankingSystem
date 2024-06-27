package com.example.bankingsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private LocalDateTime date;
    private double amount;
    private String payee;
    @ApiModelProperty(allowableValues = "WITHDRAWAL, BILL_PAYMENT", example = "WITHDRAWAL")
    private String transactionType;
}
