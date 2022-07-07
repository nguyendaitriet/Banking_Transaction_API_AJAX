package com.banking.service;

import com.banking.model.Deposit;
import com.banking.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DepositService implements IDepositService{

    @Autowired
    DepositRepository depositRepository;

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
}
