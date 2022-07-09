package com.banking.controller.rest;


import com.banking.model.Customer;
import com.banking.model.dto.TransferDTO;
import com.banking.service.CustomerService;
import com.banking.util.AppUtils;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/recipients/{senderId}")
    public ResponseEntity<?> getRecipientList(@PathVariable String senderId) {

        if (ParsingValidationUtils.isLongParsable(senderId)) {
            long validSenderId = Long.parseLong(senderId);
            Optional<Customer> senderExists = customerService.findById(validSenderId);

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

        if (!bindingResult.hasErrors()) {
            long validSenderId = Long.parseLong(transferDTO.getRecipientId());
            Optional<Customer> senderExists = customerService.findByIdAndDeletedFalse(validSenderId);
            long validRecipientId = Long.parseLong(transferDTO.getRecipientId());
            Optional<Customer> recipientExists = customerService.findByIdAndDeletedFalse(validRecipientId);

            


        }

        return appUtils.mapErrorPlus(bindingResult,errors);
    }
}
