package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Transfer;
import com.banking.model.dto.TransferDTO;

public interface ITransferService extends IGeneralService<Transfer>{

    void transfer(TransferDTO transferDTO, Customer sender, Customer recipient);
}
