package com.banking.service;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {

    CustomerDTO saveNewCustomerFromDTO(CustomerDTO customerDTO);

    CustomerDTO saveUpdatedCustomerFromDTO(CustomerDTO customerDTO, Customer customer);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<CustomerDTO> findAllCustomersDTO();

    Optional<CustomerDTO> findCustomerDTOById(Long id);

    List<CustomerDTO> findRecipients(Long senderId);

    Optional<Customer> findByIdAndDeletedFalse(Long id);

    void suspendCustomer(Long id);

    boolean existsByPhoneAndIdIsNot(String phone, Long id);

    boolean existsByEmailAndIdIsNot(String email, Long id);

}
