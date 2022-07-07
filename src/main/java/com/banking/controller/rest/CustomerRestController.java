package com.banking.controller.rest;

import com.banking.exception.DataInputEmailExist;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;
import com.banking.service.CustomerService;
import com.banking.util.AppUtils;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {

        customer.setId(0L);
        customer.setBalance(BigDecimal.ZERO);

        Map<String, String> errors = new HashMap<>();

        boolean emailExits = customerService.existsByEmail(customer.getEmail());
        boolean phoneExists = customerService.existsByPhone(customer.getPhone());

        //Gather all responses and show on view
        if (emailExits) {
            errors.put("email", "Email address exists.");
        }

        if (phoneExists) {
            errors.put("phone", "Phone number exists.");
        }

        if (errors.isEmpty()) {
            try {
                customer = customerService.save(customer);
                return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Process failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

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
            Optional<Customer> customerExists = customerService.findById(validId);

            if (customerExists.isPresent()) {

//                if (!bindingResult.hasErrors()) {

//                    Map<String, String> errors = new HashMap<>();

                    boolean emailUpdateExits = customerService.existsByEmailAndIdIsNot(customerDTO.getEmail(), validId);
                    boolean phoneUpdateExists = customerService.existsByPhoneAndIdIsNot(customerDTO.getPhone(), validId);


                    if (emailUpdateExits) {
//                       throw new DataInputEmailExist("Email address exist.");
                        bindingResult.addError(new ObjectError("email", "Email address exist."));
                    }

                    if (phoneUpdateExists) {
//                        errors.put("phone", "Phone number exists.");
                        bindingResult.addError(new ObjectError("phone", "Phone number exist."));
                    }

//                    if (errors.isEmpty()) {

                    if (!bindingResult.hasErrors()) {

                        try {

                            Customer customer = customerDTO.toCustomer();

                            customer.setId(customerExists.get().getId());
                            customer.setBalance(customerExists.get().getBalance());

                            customerService.save(customer);

                            customerDTO = customer.toCustomerDTO();

                            return new ResponseEntity<>(customerDTO, HttpStatus.OK);

                        } catch (Exception e) {

                            return new ResponseEntity<>("Process failed.", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }

//                    return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

                    return appUtils.mapError(bindingResult);
                }

//                return appUtils.mapError(bindingResult);
//            }
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
                return new ResponseEntity<>(customerExists.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Customer ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

}
