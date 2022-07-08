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

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {
                Customer customer = customerExists.get();

                new WithdrawDTO().validate(withdrawDTO, bindingResult);

                if (!bindingResult.hasErrors()) {

                    long transactionAmount = new BigDecimal(withdrawDTO.getTransactionAmount()).longValue();

                    if (transactionAmount >= 100 && transactionAmount <= 50000000) {

                        BigDecimal newBalance = customer.getBalance().subtract(new BigDecimal(transactionAmount));
                        customer.setBalance(newBalance);
                        customerService.save(customer);

                        Withdraw newWithdraw = new Withdraw();
                        newWithdraw.setTransactionAmount(new BigDecimal(transactionAmount));
                        newWithdraw.setCustomer(customer);
                        withdrawService.save(newWithdraw);

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

