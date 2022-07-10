package com.banking.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface TransferInfoDTO {
    Long getId();
    Long getSenderId();
    String getSenderName();
    Long getRecipientId();
    String getRecipientName();
    BigDecimal getTransferAmount();
    int getFees();
    BigDecimal getFeesAmount();
    Date getCreationDate();
}
