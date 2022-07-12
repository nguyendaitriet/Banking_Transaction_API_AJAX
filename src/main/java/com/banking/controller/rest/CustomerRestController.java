package com.banking.controller.rest;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;
import com.banking.service.CustomerService;
import com.banking.util.AppUtils;
import com.banking.util.ErrorMessage;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public ResponseEntity<?> getCustomerList() {

        List<CustomerDTO> customerList = customerService.findAllCustomersDTO();

        if (customerList.isEmpty()) {
            return new ResponseEntity<>("No customer found.", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody CustomerDTO customerDTO,
                                            BindingResult bindingResult) {

        boolean emailExits = customerService.existsByEmail(customerDTO.getEmail());
        boolean phoneExists = customerService.existsByPhone(customerDTO.getPhone());

        if (emailExits) {
            bindingResult.addError(new FieldError("email", "email", ErrorMessage.DUPLICATE_EMAIL));
        }

        if (phoneExists) {
            bindingResult.addError(new FieldError("phone", "phone", ErrorMessage.DUPLICATE_PHONE));
        }

        if (!bindingResult.hasErrors()) {
            try {

                customerDTO = customerService.saveNewCustomerFromDTO(customerDTO);
                return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

            } catch (Exception e) {
                return new ResponseEntity<>("Process failed. Please contact to the manager.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return appUtils.mapError(bindingResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<CustomerDTO> customerDTO = customerService.findCustomerDTOById(validId);

            if (customerDTO.isPresent()) {
                return new ResponseEntity<>(customerDTO.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Customer doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id,
                                            @Validated @RequestBody CustomerDTO customerDTO,
                                            BindingResult bindingResult) {

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findByIdAndDeletedFalse(validId);

            if (customerExists.isPresent()) {
                Customer customer = customerExists.get();

                boolean emailUpdateExits = customerService.existsByEmailAndIdIsNot(customerDTO.getEmail(), validId);
                boolean phoneUpdateExists = customerService.existsByPhoneAndIdIsNot(customerDTO.getPhone(), validId);

                if (emailUpdateExits) {
                    bindingResult.addError(new FieldError("email", "email", ErrorMessage.DUPLICATE_EMAIL));
                }

                if (phoneUpdateExists) {
                    bindingResult.addError(new FieldError("phone", "phone", ErrorMessage.DUPLICATE_PHONE));
                }

                if (!bindingResult.hasErrors()) {
                    try {

                        customerDTO = customerService.saveUpdatedCustomerFromDTO(customerDTO, customer);
                        return new ResponseEntity<>(customerDTO, HttpStatus.OK);

                    } catch (Exception e) {
                        return new ResponseEntity<>("Process failed.", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }

            return appUtils.mapError(bindingResult);
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/suspend/{id}")
    public ResponseEntity<?> suspendCustomer(@PathVariable String id) {
        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {

                customerService.suspendCustomer(validId);
                return new ResponseEntity<>(customerExists.get().toCustomerDTO(), HttpStatus.OK);

            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

}
