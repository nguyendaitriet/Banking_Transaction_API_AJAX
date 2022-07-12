package com.banking.model.dto;

import com.banking.model.Withdraw;
import com.banking.util.ErrorMessage;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class DepositDTO implements Validator {
    private String fullName;
    private String email;
    private BigDecimal balance;
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


    @Override
    public boolean supports(Class<?> clazz) {
        return DepositDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        DepositDTO depositDTO = (DepositDTO) target;
        String transactionAmount = depositDTO.getTransactionAmount();

        if (transactionAmount == null || transactionAmount.equals("")) {
            errors.rejectValue("transactionAmount", "400", ErrorMessage.EMPTY_AMOUNT);
            return;
        }

        boolean isTransactionAmountValid = java.util.regex.Pattern.matches("\\d+", transactionAmount);
        if (!isTransactionAmountValid) {
            errors.rejectValue("transactionAmount", "400",ErrorMessage.INVALID_AMOUNT_FORMAT);
            return;
        }

        if (transactionAmount.length() > 12) {
            errors.rejectValue("transactionAmount","400", ErrorMessage.MAX_AMOUNT_LENGTH);
            return;
        }

        long validTransactionAmount = Long.parseLong(transactionAmount);
        if (validTransactionAmount < 100) {
            errors.rejectValue("transactionAmount", "400", ErrorMessage.MINIMUM_TRANSACTION_AMOUNT);
            return;
        }

        if (validTransactionAmount > 50000000) {
            errors.rejectValue("transactionAmount", "400",ErrorMessage.MAXIMUM_TRANSACTION_AMOUNT);
        }

    }
}
