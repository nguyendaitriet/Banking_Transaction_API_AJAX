package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Transfer;
import com.banking.model.dto.TransferDTO;
import com.banking.model.dto.TransferInfoDTO;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransferService implements ITransferService{

    public static final int fees = 10;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransferRepository transferRepository;

    @Override
    public Iterable<Transfer> findAll() {
        return null;
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Transfer getById(Long id) {
        return null;
    }

    @Override
    public Transfer save(Transfer transfer) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void transfer(TransferDTO transferDTO, Customer sender, Customer recipient) {

        BigDecimal transferAmount = new BigDecimal(transferDTO.getTransferAmount());
        BigDecimal feesAmount = transferAmount.multiply(new BigDecimal(fees)).divide(new BigDecimal(100));
        BigDecimal totalTransactionAmount = transferAmount.add(feesAmount);

        customerRepository.decreaseBalance(sender.getId(), totalTransactionAmount);
        customerRepository.save(sender);
        sender.setBalance(sender.getBalance().subtract(totalTransactionAmount));

        customerRepository.increaseBalance(recipient.getId(), transferAmount);
        customerRepository.save(sender);
        recipient.setBalance(recipient.getBalance().add(transferAmount));

        Transfer newTransfer = transferDTO.toTransfer(transferDTO, sender, recipient);
        newTransfer.setFeesAmount(feesAmount);
        newTransfer.setTransactionAmount(totalTransactionAmount);
        newTransfer.setFees(fees);
        transferRepository.save(newTransfer);

    }

    @Override
    public List<TransferInfoDTO> getTransferInfo() {
        return transferRepository.getTransferInfo();
    };

}
