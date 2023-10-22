package com.homecode.order.controller;

import com.homecode.commons.module.dto.OrderItemDTO;
import com.homecode.order.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.module.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderItemDTO>> findAll() {
        return this.orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> findById(@PathVariable("id") Long id) {
        return this.orderItemService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderItemDTO> create(@RequestBody OrderItemDTO orderItemDTO) {
        return this.orderItemService.create(orderItemDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       return this.orderItemService.delete(id);
    }
}
