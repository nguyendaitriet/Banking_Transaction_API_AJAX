package com.banking.model.dto;

import com.banking.model.Customer;
import com.banking.model.Transfer;
import com.banking.model.Withdraw;
import com.banking.service.TransferService;
import com.banking.util.ErrorMessage;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

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
        String senderId = transferDTO.getSenderId();
        String recipientId = transferDTO.getRecipientId();
        String transferAmount = transferDTO.getTransferAmount();

        if (recipientId == null || recipientId.equals("")) {
            errors.rejectValue("recipientId","400", ErrorMessage.RECIPIENT_NOT_EMPTY);
        }

        if (senderId == null || senderId.equals("")) {
            errors.rejectValue("senderId", "400",ErrorMessage.SENDER_NOT_EMPTY);
        }

        if (transferAmount == null || transferAmount.equals("")) {
            errors.rejectValue("transferAmount", "400", ErrorMessage.EMPTY_TRANSFER_AMOUNT);
            return;
        }

        boolean isRecipientId = java.util.regex.Pattern.matches("\\d+", recipientId);
        if (!isRecipientId) {
            errors.rejectValue("recipientId","400", ErrorMessage.RECIPIENT_NOT_EXIST);
        }

        boolean isSenderId = java.util.regex.Pattern.matches("\\d+", recipientId);
        if (!isSenderId) {
            errors.rejectValue("senderId", "400", ErrorMessage.SENDER_NOT_EXIST);
        }

        boolean isTransferAmountValid = java.util.regex.Pattern.matches("\\d+", transferAmount);
        if (!isTransferAmountValid) {
            errors.rejectValue("transferAmount", "400",ErrorMessage.INVALID_AMOUNT_FORMAT);
            return;
        }

        if (transferAmount.length() > 12) {
            errors.rejectValue("transferAmount","400", ErrorMessage.MAX_AMOUNT_LENGTH);
            return;
        }

        long validTransferAmount = Long.parseLong(transferAmount);
        if (validTransferAmount < 100) {
            errors.rejectValue("transferAmount", "400", ErrorMessage.MINIMUM_TRANSACTION_AMOUNT);
            return;
        }

        if (validTransferAmount > 50000000) {
            errors.rejectValue("transferAmount", "400",ErrorMessage.MAXIMUM_TRANSACTION_AMOUNT);
        }

    }

    public Transfer toTransfer(TransferDTO transferDTO, Customer sender, Customer recipient) {
        Transfer newTransfer = new Transfer();

        newTransfer.setSender(sender);
        newTransfer.setRecipient(recipient);
        newTransfer.setFees(TransferService.fees);
        newTransfer.setTransferAmount(new BigDecimal(transferDTO.getTransferAmount()));

        return newTransfer;
    }
}
