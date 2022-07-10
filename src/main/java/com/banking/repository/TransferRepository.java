package com.banking.repository;

import com.banking.model.Transfer;
import com.banking.model.dto.TransferInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT NEW com.banking.model.dto.TransferInfoDTO (" +
                "t.id AS id, " +
                "t.sender.id AS senderId, " +
                "t.sender.fullName AS senderName, " +
                "t.recipient.id AS recipientId, " +
                "t.recipient.fullName AS recipientName, " +
                "t.transferAmount AS transferAmount, " +
                "t.fees AS fees, " +
                "t.feesAmount AS feesAmount, " +
                "t.createdAt AS creationDate " +
            ") " +
            "FROM Transfer AS t " +
            "INNER JOIN Customer AS c1 ON c1.id = t.sender.id " +
            "INNER JOIN Customer AS c2 ON c2.id = t.recipient.id " +
            "ORDER BY t.id ")
    TransferInfoDTO getTransferInfo();


}
