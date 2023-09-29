package com.homecode.BoutiqueApp.controller;

import com.homecode.BoutiqueApp.model.dto.OrderItemDTO;
import com.homecode.BoutiqueApp.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.BoutiqueApp.constants.Web.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemDTO> findAll() {
        return this.orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemDTO findById(@PathVariable("id") Long id) {
        return this.orderItemService.findById(id);
    }

    @PostMapping("/create")
    public OrderItemDTO create(@RequestBody OrderItemDTO orderItemDTO) {
        return this.orderItemService.create(orderItemDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.orderItemService.delete(id);
    }
}
