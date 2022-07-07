package com.banking.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12)
    @NotNull(message = "Transaction amount must NOT be empty.")
    @Digits(integer = 12, fraction = 0,
            message = "Maximum digit of transaction amount is 12.")
    @Min(value = 100,message = "Transaction amount must NOT be LESS than 100.")
    private BigDecimal transferAmount;

    @Column(nullable = false, precision = 12)
    @NotNull(message = "Total amount must NOT be empty.")
    @Digits(integer = 12, fraction = 0,
            message = "Maximum digit of Total amount is 12.")
    private BigDecimal transactionAmount;

    @Column(nullable = false)
    private int fees;

    @Column(precision = 12)
    private BigDecimal feesAmount;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private  Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private  Customer recipient;

    public Transfer() {
    }

    public Transfer(Long id, BigDecimal transferAmount, BigDecimal transactionAmount, int fees, BigDecimal feesAmount, Customer sender, Customer recipient) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.transactionAmount = transactionAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}
