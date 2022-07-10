package com.banking.controller.rest;

import com.banking.model.Customer;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.TransferDTO;
import com.banking.model.dto.TransferInfoDTO;
import com.banking.service.CustomerService;
import com.banking.service.TransferService;
import com.banking.util.AppUtils;
import com.banking.util.ErrorMessage;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/recipients/{senderId}")
    public ResponseEntity<?> getRecipientList(@PathVariable String senderId) {

        if (ParsingValidationUtils.isLongParsable(senderId)) {
            long validSenderId = Long.parseLong(senderId);
            Optional<Customer> senderExists = customerService.findByIdAndDeletedFalse(validSenderId);

            if (senderExists.isPresent()) {
                return new ResponseEntity<> (customerService.findRecipients(validSenderId), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<?> transfer(@Validated @RequestBody TransferDTO transferDTO,
                                      BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        new TransferDTO().validate(transferDTO, bindingResult);

        if (!bindingResult.hasErrors()) {
            long validSenderId = Long.parseLong(transferDTO.getSenderId());
            Optional<Customer> senderExists = customerService.findByIdAndDeletedFalse(validSenderId);
            long validRecipientId = Long.parseLong(transferDTO.getRecipientId());
            Optional<Customer> recipientExists = customerService.findByIdAndDeletedFalse(validRecipientId);

            if (validSenderId == validRecipientId) {
                errors.put("duplicateId", ErrorMessage.DUPLICATE_SENDER);
            }

            if (!senderExists.isPresent()) {
                errors.put("senderId", ErrorMessage.SENDER_NOT_EXIST);
            }

            if (!recipientExists.isPresent()) {
                errors.put("recipientId", ErrorMessage.RECIPIENT_NOT_EXIST);
            }

            if (errors.isEmpty()) {
                Customer sender = senderExists.get();
                Customer recipient = recipientExists.get();

                BigDecimal transferAmount = new BigDecimal(transferDTO.getTransferAmount());
                BigDecimal feesAmount = transferAmount.multiply(new BigDecimal(TransferService.fees)).divide(new BigDecimal(100));
                BigDecimal totalTransactionAmount = transferAmount.add(feesAmount);
                BigDecimal senderBalance = sender.getBalance();

                if (senderBalance.compareTo(totalTransactionAmount) >= 0) {

                    transferService.transfer(transferDTO, sender, recipient);

                    Map<String, CustomerDTO> updatedCustomers = new HashMap<>();
                    updatedCustomers.put("sender", sender.toCustomerDTO());
                    updatedCustomers.put("recipient", recipient.toCustomerDTO());

                    return new ResponseEntity<>(updatedCustomers, HttpStatus.OK);
                }

                errors.put("transferAmount", ErrorMessage.MAXIMUM_WITHDRAW_AMOUNT);
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return appUtils.mapError(bindingResult);
    }

    @GetMapping("/showTransferInfo")
    public ResponseEntity<?> getTransferInfo() {
        List<TransferInfoDTO> transferInfoDTO = transferService.getTransferInfo();
        return new ResponseEntity<>(transferInfoDTO, HttpStatus.OK);
    }
}
