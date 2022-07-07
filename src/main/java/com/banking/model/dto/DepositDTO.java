package com.banking.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class DepositDTO {
    private String fullName;
    private String email;
    private BigDecimal balance;

    @NotEmpty
    @Size(max = 12,
    message = "Max length of transaction amount: 12.")
    @Pattern(regexp = "\\d+",
            message = "Transaction amount contains number only.")
    private String transactionAmount;

    public DepositDTO() {
    }

    public DepositDTO(String fullName, String email, BigDecimal balance, String transactionAmount) {
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
        this.transactionAmount = transactionAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
