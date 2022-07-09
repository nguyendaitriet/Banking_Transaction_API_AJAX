package com.banking.controller.rest;


import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.Withdraw;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.DepositDTO;
import com.banking.model.dto.WithdrawDTO;
import com.banking.service.CustomerService;
import com.banking.service.WithdrawService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/withdraws")
public class WithdrawRestController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AppUtils appUtils;

    @Autowired
    WithdrawService withdrawService;

    @PostMapping("/{id}")
    public ResponseEntity<?> withdraw(@PathVariable String id,
                                      @Validated @RequestBody WithdrawDTO withdrawDTO,
                                      BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {
                Customer customer = customerExists.get();

                long transactionAmount = new BigDecimal(withdrawDTO.getTransactionAmount()).longValue();
                long customerBalance = customer.getBalance().longValue();

                if (transactionAmount > customerBalance) {
                    errors.put("amountWit", ErrorMessage.MAXIMUM_WITHDRAW_AMOUNT);
                }

                new WithdrawDTO().validate(withdrawDTO, bindingResult);

                if (!bindingResult.hasErrors() && errors.isEmpty()) {

                        CustomerDTO customerDTO = withdrawService.withdraw(withdrawDTO, customer);
                        return new ResponseEntity<>(customerDTO, HttpStatus.OK);

                }

                return appUtils.mapErrorPlus(bindingResult, errors);
            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

}

