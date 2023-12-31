package com.homecode.order.controller;

import com.homecode.order.model.dto.PaymentDTO;
import com.homecode.order.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.order.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> findAll() {
        return this.paymentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable("id") Long id) {
        return this.paymentService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> create(@RequestBody PaymentDTO paymentDTO) {
        return this.paymentService.create(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       return this.paymentService.delete(id);
    }
}
