package com.banking.model.dto;

import com.banking.model.Withdraw;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TransferDTO implements Validator {

    @NotEmpty (message = "Sender ID must NOT be empty.")
    @Pattern(regexp = "\\d+",
            message = "Sender ID contains number only.")
    private String senderId;

    @NotEmpty (message = "Recipient ID must NOT be empty.")
    @Pattern(regexp = "\\d+",
            message = "Recipient ID contains number only.")
    private String recipientId;

    @NotEmpty (message = "Transfer amount must NOT be empty.")
    @Size(max = 12,
    message = "Max length of transfer amount: 12.")
    @Pattern(regexp = "\\d+",
            message = "Transfer amount contains number only.")
    private String transferAmount;

    public TransferDTO() {
    }

    public TransferDTO(String senderId, String recipientId, String transferAmount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.transferAmount = transferAmount;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Withdraw.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransferDTO transferDTO = (TransferDTO) target;
        String recipientId = transferDTO.getRecipientId();
        String transferAmount = getTransferAmount();

        if (recipientId == null || recipientId.equals("")) {
            errors.rejectValue("recipient", "recipient.emptyRecipientId");
        }

        if (transferAmount == null || transferAmount.equals("")) {
            errors.rejectValue("transferAmount", "transferAmount.emptyTransferAmount");
            return;
        }

        boolean isRecipientId = java.util.regex.Pattern.matches("\\d+", recipientId);
        if (!isRecipientId) {
            errors.rejectValue("recipient", "recipient.validFormat");
        }

        boolean isTransferAmountValid = java.util.regex.Pattern.matches("\\d+", transferAmount);
        if (!isTransferAmountValid) {
            errors.rejectValue("transferAmount", "transferAmount.validFormat");
        }

    }
}
