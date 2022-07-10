package com.banking.repository;

import com.banking.model.Transfer;
import com.banking.model.dto.TransferInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT " +
                "t.id AS id, " +
                "t.sender.id AS senderId, " +
                "t.sender.fullName AS senderName, " +
                "t.recipient.id AS recipientId, " +
                "t.recipient.fullName AS recipientName, " +
                "t.transferAmount AS transferAmount, " +
                "t.fees AS fees, " +
                "t.feesAmount AS feesAmount, " +
                "t.createdAt AS creationDate " +
            "FROM Transfer AS t "
    )
    List<TransferInfoDTO> getTransferInfo();


}
