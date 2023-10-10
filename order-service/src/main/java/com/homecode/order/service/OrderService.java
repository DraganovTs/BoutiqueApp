package com.homecode.order.service;


import com.homecode.commons.dto.OrderDTO;
import com.homecode.commons.exception.CustomDatabaseOperationException;
import com.homecode.commons.exception.CustomNotFoundException;
import com.homecode.commons.exception.CustomValidationException;
import com.homecode.order.model.Order;
import com.homecode.order.model.enums.OrderStatus;
import com.homecode.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public Order create(Long cartId) {
        log.debug("Request to create  Order whit a Cart : {}", cartId);

        try {
            Order order = new Order(
                    BigDecimal.ZERO,
                    OrderStatus.CREATION,
                    null,
                    null,
                    null,
                    Collections.emptySet(),
                    cartId
            );
            this.orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new CustomDatabaseOperationException(e.getMessage(), "DATABASE_OPERATION_EXCEPTION");
        }


    }

    public ResponseEntity<OrderDTO> create(OrderDTO orderDTO) {
        log.debug("Request to create Order : {}", orderDTO);
        Order order = mapToOrder(orderDTO);
        try {
            this.orderRepository.save(order);
            return new ResponseEntity<>(mapToDto(order), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException(e.getMessage(), "DATABASE_OPERATION_EXCEPTION");
        }
    }


    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderDTO>> findAll() {
        log.debug("Request to get all Orders");

        List<OrderDTO> orders = this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
        if (orders.isEmpty()) {
            throw new CustomNotFoundException("No categories available.",
                    "CATEGORIES_NOT_FOUND");
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderDTO>> findAllByUser(Long id) {
        log.debug("Request to get all Orders by Customer id {}", id);

        List<OrderDTO> userOrders = this.orderRepository.findAllByCartId(id)
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());

        if (userOrders.isEmpty()) {
            throw new CustomNotFoundException("Not found orders for user " + id,
                    "ORDERS_NOT_FOUND");
        }
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }

    public ResponseEntity<OrderDTO> findById(Long id) {
        log.debug("Request to get Order by id {}", id);
        OrderDTO order = this.orderRepository.findById(id).map(OrderService::mapToDto)
                .orElse(null);
        if (order == null) {
            throw new CustomNotFoundException("Not found order whit id " + id,
                    "ORDER_NOT_FOUND");
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        try {
            this.orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomNotFoundException("Not found order whit id " + id,
                    "ORDER_NOT_FOUND");
        }
        this.orderRepository.deleteById(id);
    }

    public static OrderDTO mapToDto(Order order) {
        if (order != null) {
            return new OrderDTO(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getStatus().name(),
                    order.getShipped(),
                    PaymentService.mapToDTO(order.getPayment()),
                    AddressService.mapToDTO(order.getShipmentAddress()),
                    order.getOrderItems()
                            .stream()
                            .map(OrderItemService::mapToDto)
                            .collect(Collectors.toSet())
            );

        }
        return null;
    }

    private Order mapToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            throw new CustomValidationException("Not valid order",
                    "ORDER_NOT_VALID");
        }
        return new Order(
                BigDecimal.ZERO,
                OrderStatus.CREATION,
                null,
                null,
                null,
                Collections.emptySet(),
                null
        );
    }
}
