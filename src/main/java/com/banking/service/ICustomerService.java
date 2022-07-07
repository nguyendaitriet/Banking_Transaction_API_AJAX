package com.banking.service;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<CustomerDTO> findAllCustomersDTO();

    Optional<CustomerDTO> findCustomerDTOById(Long id);

    void suspendCustomer(Long id);

    boolean existsByPhoneAndIdIsNot(String phone, Long id);

    boolean existsByEmailAndIdIsNot(String email, Long id);

}
