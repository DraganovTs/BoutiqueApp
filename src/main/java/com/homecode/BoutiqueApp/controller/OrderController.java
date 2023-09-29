package com.homecode.BoutiqueApp.controller;

import com.homecode.BoutiqueApp.model.dto.OrderDTO;
import com.homecode.BoutiqueApp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.BoutiqueApp.constants.Web.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> findAll() {
        return this.orderService.findAll();
    }

    @GetMapping("/customer/{id}")
    public List<OrderDTO> findAllByUser(@PathVariable Long id) {
        return this.orderService.findAllByUser(id);
    }

    @PostMapping("/create")
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return this.orderService.create(orderDTO);
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable("id") Long id) {
        return this.orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.orderService.delete(id);
    }
}
