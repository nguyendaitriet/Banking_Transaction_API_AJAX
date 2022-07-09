package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.Withdraw;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.WithdrawDTO;
import com.banking.repository.CustomerRepository;
import com.banking.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class WithdrawService implements IWithdrawService{

    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Iterable<Withdraw> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Withdraw getById(Long id) {
        return null;
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public CustomerDTO withdraw(WithdrawDTO withdrawDTO, Customer customer) {

        BigDecimal transactionAmount = new BigDecimal(withdrawDTO.getTransactionAmount());
        customerRepository.decreaseBalance(customer.getId(), transactionAmount);

        Withdraw newWithdraw = new Withdraw();
        newWithdraw.setTransactionAmount(transactionAmount);
        newWithdraw.setCustomer(customer);
        withdrawRepository.save(newWithdraw);

        customer.setBalance(customer.getBalance().subtract(transactionAmount));

        return customer.toCustomerDTO();
    }
}
