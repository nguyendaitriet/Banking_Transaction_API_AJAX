package com.banking.controller.rest;

import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.DepositDTO;
import com.banking.service.CustomerService;
import com.banking.service.DepositService;
import com.banking.util.AppUtils;
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
@RequestMapping("/api/deposits")
public class DepositRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private AppUtils appUtils;

    @PutMapping("/{id}")
    public ResponseEntity<?> deposit(@PathVariable String id,
                                     @RequestBody DepositDTO depositDTO,
                                     BindingResult bindingResult) {

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {
                Customer customer = customerExists.get();

                new DepositDTO().validate(depositDTO, bindingResult);

                if (!bindingResult.hasErrors()) {

                    try {

                        CustomerDTO customerDTO = depositService.deposit(depositDTO, customer);
                        return new ResponseEntity<>(customerDTO, HttpStatus.OK);

                    } catch (Exception e) {
                        return new ResponseEntity<>("Process failed.", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

                return appUtils.mapError(bindingResult);
            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

}
