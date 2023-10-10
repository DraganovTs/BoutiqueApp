package com.homecode.customer.service;


import com.homecode.commons.dto.CartDTO;
import com.homecode.commons.dto.OrderDTO;
import com.homecode.commons.exception.CustomDatabaseOperationException;
import com.homecode.commons.exception.CustomIllegalStateException;
import com.homecode.commons.exception.CustomNotFoundException;
import com.homecode.customer.feign.OrderServiceInterface;
import com.homecode.customer.model.Cart;
import com.homecode.customer.model.Customer;
import com.homecode.customer.model.enums.CartStatus;
import com.homecode.customer.repository.CartRepository;
import com.homecode.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderServiceInterface orderService;

    @Transactional(readOnly = true)
    public ResponseEntity<List<CartDTO>> findAll() {
        log.debug("Request to get all Carts");

        List<CartDTO> cart = this.cartRepository.findAll()
                .stream()
                .map(CartService::mapToDTO)
                .collect(Collectors.toList());
        if (cart.isEmpty()) {
            throw new CustomNotFoundException("No carts available.",
                    "CARTS_NOT_FOUND");
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<CartDTO>> findAllActiveCarts() {
        log.debug("Request to get all active Carts");

        List<CartDTO> activeCarts = this.cartRepository.findByStatus(CartStatus.NEW)
                .stream()
                .map(CartService::mapToDTO)
                .collect(Collectors.toList());
        if (activeCarts.isEmpty()) {
            throw new CustomNotFoundException("No active carts available.",
                    "ACTIVE_CARTS_NOT_FOUND");
        }

        return new ResponseEntity<>(activeCarts, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CartDTO> getActiveCart(Long customerId) {
        log.debug("Request to get active Cart for customer whit id : {}", customerId);

        List<Cart> carts = this.cartRepository.findByStatusAndCustomerId(
                CartStatus.NEW, customerId);
        if (carts.size() == 1) {
            return new ResponseEntity<>(mapToDTO(carts.get(0)), HttpStatus.OK);
        }
        if (carts.size() > 1) {
            throw new CustomIllegalStateException("Many active carts detected!!!",
                    "MANY_CARTS_FOUND");
        }
        return null;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CartDTO> findById(Long id) {
        log.debug("Request to get a Cart : {}", id);

        CartDTO cart = this.cartRepository.findById(id)
                .map(CartService::mapToDTO)
                .orElse(null);
        if (cart == null) {
            throw new CustomNotFoundException("Not found cart whit id " + id,
                    "CART_NOT_FOUND");
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<CartDTO> create(Long customerId) {
        log.debug("Request to create a Cart whit customer id : {}", customerId);
        if (this.getActiveCart(customerId) == null) {
            Customer customer = this.customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomNotFoundException("Not found customer whit id " + customerId,
                            "CUSTOMER_NOT_FOUND"));

            Cart cart = new Cart(
                    null,
                    customer,
                    CartStatus.NEW
            );
            OrderDTO order = this.orderService.create(mapToDTO(cart));
            cart.setOrderId(order.getId());
            try {
                this.cartRepository.save(cart);
                return new ResponseEntity<>(mapToDTO(cart), HttpStatus.CREATED);
            } catch (Exception e) {
                throw new CustomDatabaseOperationException(e.getMessage(), "DATABASE_OPERATION_EXCEPTION");
            }
        } else {
            throw new CustomIllegalStateException("Many active carts detected!!!",
                    "MANY_CARTS_FOUND");
        }
    }

    public void delete(Long id) {
        log.debug("Request to delete a Cart : {}", id);
        try {
            this.cartRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomNotFoundException("Not found cart whit id " + id,
                    "CART_NOT_FOUND");
        }
    }

    public static CartDTO mapToDTO(Cart cart) {
        if (cart != null) {
            return new CartDTO(
                    cart.getId(),
                    cart.getOrderId(),
                    CustomerService.mapToDTO(cart.getCustomer()),
                    cart.getStatus().name()

            );
        }
        return null;
    }
}
