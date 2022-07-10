package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.Transfer;
import com.banking.model.dto.TransferDTO;
import com.banking.model.dto.TransferInfoDTO;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer>{

    void transfer(TransferDTO transferDTO, Customer sender, Customer recipient);
    List<TransferInfoDTO> getTransferInfo();


}
