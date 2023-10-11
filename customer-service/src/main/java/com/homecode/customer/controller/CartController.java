package com.homecode.customer.controller;

import com.homecode.commons.dto.CartDTO;
import com.homecode.customer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@RequiredArgsConstructor
@RestController
@RequestMapping(API +"/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("/all")
    public ResponseEntity<List<CartDTO>> findAll(){
        return this.cartService.findAll();
    }

    @GetMapping("/active")
    private ResponseEntity<List<CartDTO>> findAllActiveCarts(){
        return this.cartService.findAllActiveCarts();
    }

    @GetMapping("/customer/carts")
    public ResponseEntity<CartDTO> getActiveCartForCustomer(@RequestParam Long customerId){
        return this.cartService.getActiveCart(customerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> findById(@PathVariable("id") Long id){
        return this.cartService.findById(id);
    }

    @PostMapping("/customer/create")
    public ResponseEntity<CartDTO> create(@RequestParam Long customerId){
        return this.cartService.create(customerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
       return this.cartService.delete(id);
    }
}
