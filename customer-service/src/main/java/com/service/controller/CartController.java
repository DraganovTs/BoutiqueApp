package com.service.controller;

import com.homecode.commons.dto.CartDTO;
import com.service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@RequiredArgsConstructor
@RestController
@RequestMapping(API +"/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartDTO> findAll(){
        return this.cartService.findAll();
    }

    @GetMapping("/active")
    private List<CartDTO> findAllActiveCarts(){
        return this.cartService.findAllActiveCarts();
    }

    @GetMapping("/customer/{id}")
    public CartDTO getActiveCartForCustomer(@PathVariable("id") Long customerId){
        return this.cartService.getActiveCart(customerId);
    }

    @GetMapping("/{id}")
    public CartDTO findById(@PathVariable("id") Long id){
        return this.cartService.findById(id);
    }

    @PostMapping("/customer/{id}")
    public CartDTO create(@PathVariable("id") Long customerId){
        return this.cartService.create(customerId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.cartService.delete(id);
    }
}
