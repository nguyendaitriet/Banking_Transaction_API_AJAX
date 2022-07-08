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
                                     @Validated @RequestBody DepositDTO depositDTO,
                                     BindingResult bindingResult) {

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {
                Customer customer = customerExists.get();

                new DepositDTO().validate(depositDTO, bindingResult);

                if (!bindingResult.hasErrors()) {

                    long transactionAmount = new BigDecimal(depositDTO.getTransactionAmount()).longValue();

                    if (transactionAmount >= 100 && transactionAmount <= 50000000) {

                        BigDecimal newBalance = customer.getBalance().add(new BigDecimal(transactionAmount));
                        customer.setBalance(newBalance);
                        customerService.save(customer);

                        Deposit newDeposit = new Deposit();
                        newDeposit.setTransactionAmount(new BigDecimal(transactionAmount));
                        newDeposit.setCustomer(customer);
                        depositService.save(newDeposit);

                        CustomerDTO customerDTO = customer.toCustomerDTO();
                        return new ResponseEntity<>(customerDTO, HttpStatus.OK);

                    }

                    return new ResponseEntity<>("Transaction Amount must greater than 100 and less than 50,000,000", HttpStatus.BAD_REQUEST);
                }

                return appUtils.mapError(bindingResult);
            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

}
