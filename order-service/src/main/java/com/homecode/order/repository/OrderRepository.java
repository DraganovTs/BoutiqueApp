package com.homecode.order.repository;

import com.homecode.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
     List<Order> findAllByCartId(Long id);
}
