package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Withdraw;
import com.banking.model.dto.CustomerDTO;
import com.banking.model.dto.DepositDTO;
import com.banking.model.dto.WithdrawDTO;

public interface IWithdrawService extends IGeneralService<Withdraw>{

    CustomerDTO withdraw(WithdrawDTO withdrawDTO, Customer customer);

}
