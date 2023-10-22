package com.homecode.order.service;


import com.homecode.dto.CartDTO;
import com.homecode.dto.OrderDTO;
import com.homecode.exception.CustomDatabaseOperationException;
import com.homecode.exception.CustomNotFoundException;
import com.homecode.exception.CustomValidationException;
import com.homecode.order.model.Address;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDTO create(CartDTO cartDTO) {
        log.debug("Request to create  Order whit a Cart : {}", cartDTO);

        try {
            Order order = new Order(
                    BigDecimal.ZERO,
                    OrderStatus.CREATION,
                    null,
                    null,
                    null,
                    Collections.emptySet(),
                    cartDTO.getId()
            );
            //todo set address from user info
            Address shipmentAddress = new Address();
            shipmentAddress.setAddress1("123 Main St");
            shipmentAddress.setAddress2("1232 Main St");
            shipmentAddress.setCity("CityName");
            shipmentAddress.setPostcode("9000");
            shipmentAddress.setCountry("bg");

            order.setShipmentAddress(shipmentAddress);
            this.orderRepository.save(order);
            return mapToDto(order);
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
            throw new CustomDatabaseOperationException("An error occurred while creating order"
                    , "DATABASE_OPERATION_EXCEPTION");
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
            throw new CustomNotFoundException("No orders available.",
                    "ORDERS_NOT_FOUND");
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

    public ResponseEntity<?> delete(Long id) {
        log.debug("Request to delete Order : {}", id);

        Optional<Order> optionalOrder = this.orderRepository.findById(id);
        if (optionalOrder.isEmpty()){
            throw new CustomNotFoundException("Not found order whit id " + id,
                    "ORDER_NOT_FOUND");
        }
        try {
            this.orderRepository.deleteById(id);
            return new ResponseEntity<>("Delete order whit id " + id, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while deleting order  with ID: " + id,
                    "DATABASE_OPERATION_EXCEPTION");
        }
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
