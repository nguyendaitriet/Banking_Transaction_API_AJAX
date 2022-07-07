package com.banking.model.dto;

import com.banking.model.Customer;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class CustomerDTO {

    private Long id;

    @NotEmpty(message = "Name must NOT be empty.")
    @Length(min = 1, max = 150,
            message = "Max characters of name: 150.")
    @Pattern(regexp = "^[A-Za-z\\s]*",
            message = "Name contains only letter and whitespace.")
    private String fullName;

    @NotEmpty(message = "Email must NOT be empty.")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z\\d_-]+(\\.[A-Za-z\\d_-]+)*@[^-][A-Za-z\\d-]+(\\.[A-Za-z\\d-]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email address. Valid email example: john_11@gmail.com")
    private String email;

    @NotEmpty(message = "Phone number must NOT be empty.")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
            message = "Incorrect phone number format. Correct format: +1 (608) 468-6527.")

    private String phone;

    private String address;
    private BigDecimal balance;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String fullName, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
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

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setFullName(this.fullName);
        customer.setPhone(this.phone);
        customer.setAddress(this.address);
        customer.setEmail(this.email);
        return customer;
    }
}
