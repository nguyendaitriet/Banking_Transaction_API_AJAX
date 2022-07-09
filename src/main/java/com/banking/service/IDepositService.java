package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.DepositDTO;

public interface IDepositService extends IGeneralService<Deposit>{

    CustomerDTO deposit(DepositDTO depositDTO, Customer customer);

}
