package com.homecode.customer.service;


import com.homecode.commons.dto.CartDTO;
import com.homecode.customer.model.Cart;
import com.homecode.customer.model.Customer;
import com.homecode.customer.model.enums.CartStatus;
import com.homecode.customer.repository.CartRepository;
import com.homecode.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional(readOnly = true)
    public List<CartDTO> findAll() {
        log.debug("Request to get all Carts");
        return this.cartRepository.findAll()
                .stream()
                .map(CartService::mapToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CartDTO> findAllActiveCarts() {
        log.debug("Request to get all active Carts");
        return this.cartRepository.findByStatus(CartStatus.NEW)
                .stream()
                .map(CartService::mapToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public CartDTO getActiveCart(Long customerId) {
        log.debug("Request to get active Cart for customer whit id : {}", customerId);
        List<Cart> carts = this.cartRepository.findByStatusAndCustomerId(
                CartStatus.NEW,customerId);
        if (carts.size() ==1){
            return mapToDTO(carts.get(0));
        }
        if (carts.size()>1){
            throw new IllegalStateException("Many active carts detected!!!");
        }
        return null;
    }
    @Transactional(readOnly = true)
    public CartDTO findById(Long id) {
        log.debug("Request to get a Cart : {}", id);
        return this.cartRepository.findById(id)
                .map(CartService::mapToDTO)
                .orElse(null);
    }

    public CartDTO create(Long customerId) {
        log.debug("Request to create a Cart whit customer id : {}", customerId);
        if (this.getActiveCart(customerId)==null){
            Customer customer = this.customerRepository.findById(customerId)
                    .orElseThrow(()->new IllegalStateException("The customer does not exist!!!"));

            Cart cart = new Cart(
                    null,
                    customer,
                    CartStatus.NEW
            );

//            Order order = this.orderService.create(cart);
//            cart.setOrder(order);

            return mapToDTO(this.cartRepository.save(cart));
        }else {
            throw new IllegalStateException("There is already active cart!!!!");
        }
    }

    public void delete(Long id) {
        log.debug("Request to delete a Cart : {}", id);
        this.cartRepository.deleteById(id);
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
