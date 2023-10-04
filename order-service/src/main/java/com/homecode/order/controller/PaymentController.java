package com.homecode.order.controller;

import com.homecode.commons.dto.PaymentDTO;
import com.homecode.order.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    public List<PaymentDTO> findAll() {
        return this.paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentDTO findById(@PathVariable("id") Long id) {
        return this.paymentService.findById(id);
    }

    @PostMapping("/create")
    public PaymentDTO create(@RequestBody PaymentDTO paymentDTO) {
        return this.paymentService.create(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.paymentService.delete(id);
    }
}
