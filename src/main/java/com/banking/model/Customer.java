package com.banking.model;

import com.banking.model.dto.CustomerDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(length = 300)
    private String address;

    @Column(precision = 12, columnDefinition = "decimal default 0")
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Deposit> deposits;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Withdraw> withdraws;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Deposit> transfers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Transfer> senders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    private List<Transfer> recipients;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, String address, BigDecimal balance, List<Deposit> deposits, List<Withdraw> withdraws, List<Transfer> senders, List<Transfer> recipients) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.deposits = deposits;
        this.withdraws = withdraws;
        this.senders = senders;
        this.recipients = recipients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public List<Deposit> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Deposit> transfers) {
        this.transfers = transfers;
    }

    public List<Transfer> getSenders() {
        return senders;
    }

    public void setSenders(List<Transfer> senders) {
        this.senders = senders;
    }

    public List<Transfer> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Transfer> recipients) {
        this.recipients = recipients;
    }

    public CustomerDTO toCustomerDTO () {

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(this.getId());
        customerDTO.setFullName(this.fullName);
        customerDTO.setEmail(this.email);
        customerDTO.setAddress(this.address);
        customerDTO.setPhone(this.phone);
        customerDTO.setBalance(this.balance);

        return customerDTO;
    }
}

