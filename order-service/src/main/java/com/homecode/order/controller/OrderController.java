package com.homecode.order.controller;

import com.homecode.commons.dto.CartDTO;
import com.homecode.commons.dto.OrderDTO;
import com.homecode.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> findAll() {
        return this.orderService.findAll();
    }

    @GetMapping("/customer/orders")
    public ResponseEntity<List<OrderDTO>> findAllByUser(@RequestBody Long id) {
        return this.orderService.findAllByUser(id);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        return this.orderService.create(orderDTO);
    }

    @PostMapping("/createCart")
    public OrderDTO create(@RequestBody CartDTO cartDTO) {
        return this.orderService.create(cartDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable("id") Long id) {
        return this.orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.orderService.delete(id);
    }
}
