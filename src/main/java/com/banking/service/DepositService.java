package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.DepositDTO;
import com.banking.repository.CustomerRepository;
import com.banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class DepositService implements IDepositService{

    @Autowired
    DepositRepository depositRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Iterable<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deposit getById(Long id) {
        return null;
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public CustomerDTO deposit(DepositDTO depositDTO, Customer customer) {

        BigDecimal transactionAmount = new BigDecimal(depositDTO.getTransactionAmount());
        customerRepository.increaseBalance(customer.getId(), transactionAmount);

        Deposit newDeposit = new Deposit();
        newDeposit.setTransactionAmount(transactionAmount);
        newDeposit.setCustomer(customer);
        depositRepository.save(newDeposit);

        customer.setBalance(customer.getBalance().add(transactionAmount));

        return customer.toCustomerDTO();
    }

}
