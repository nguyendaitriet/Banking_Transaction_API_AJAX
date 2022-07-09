package com.banking.service;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return null;
    }

    @Override
    public List<CustomerDTO> findAllCustomersDTO() {
        return customerRepository.findAllCustomersDTO();
    }

    @Override
    public Optional<CustomerDTO> findCustomerDTOById(Long id) {
        return customerRepository.findCustomerDTOById(id);
    }

    @Override
    public List<CustomerDTO> findRecipients(Long senderId) {
        return customerRepository.findRecipients(senderId);
    }

    @Override
    public Optional<Customer> findByIdAndDeletedFalse(Long id) {
        return customerRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByPhoneAndIdIsNot(String phone, Long id) {
        return customerRepository.existsByPhoneAndIdIsNot(phone, id);
    }

    @Override
    public boolean existsByEmailAndIdIsNot(String email, Long id) {
        return customerRepository.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDTO saveNewCustomerFromDTO(CustomerDTO customerDTO) {
        Customer customer = customerDTO.toCustomer();
        customer.setId(0L);
        customer.setBalance(BigDecimal.ZERO);
        return save(customer).toCustomerDTO();
    }

    @Override
    public CustomerDTO saveUpdatedCustomerFromDTO(CustomerDTO customerDTO, Customer customer) {
        Customer customerUpdated = customerDTO.toCustomer();
        customerUpdated.setId(customer.getId());
        customerUpdated.setBalance(customer.getBalance());
        return save(customerUpdated).toCustomerDTO();
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void suspendCustomer(Long id) {
        customerRepository.suspendCustomer(id);
    }

}
