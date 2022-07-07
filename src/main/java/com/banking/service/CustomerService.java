package com.banking.service;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public void remove(Long id) {

    }

    @Override
    public void suspendCustomer(Long id) {
        customerRepository.suspendCustomer(id);
    }

}
