package com.homecode.customer.repository;

import com.homecode.customer.model.entity.Cart;
import com.homecode.customer.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByStatus(CartStatus status);
    List<Cart> findByStatusAndCustomerId(CartStatus status,Long customerId);
}
