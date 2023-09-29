package com.homecode.BoutiqueApp.repository;

import com.homecode.BoutiqueApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByEnabled(Boolean enabled);
}
