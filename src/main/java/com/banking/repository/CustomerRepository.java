package com.banking.repository;

import com.banking.model.dto.CustomerDTO;
import com.banking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT NEW com.banking.model.dto.CustomerDTO (" +
                "c.id, " +
                "c.fullName, " +
                "c.email, " +
                "c.phone, " +
                "c.address, " +
                "c.balance" +
            ") " +
            "FROM Customer c " +
            "WHERE c.deleted = false")
    List<CustomerDTO> findAllCustomersDTO();

    @Query("SELECT NEW com.banking.model.dto.CustomerDTO (" +
                "c.id, " +
                "c.fullName, " +
                "c.email, " +
                "c.phone, " +
                "c.address, " +
                "c.balance" +
            ") " +
            "FROM Customer c " +
            "WHERE c.id = :id AND c.deleted = false ")
    Optional<CustomerDTO> findCustomerDTOById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c " +
            "SET c.deleted = TRUE " +
            "WHERE c.id = :id")
    void suspendCustomer(@Param("id") long id);

    boolean existsByPhoneAndIdIsNot(String phone, Long id);

    boolean existsByEmailAndIdIsNot(String email, Long id);

}
