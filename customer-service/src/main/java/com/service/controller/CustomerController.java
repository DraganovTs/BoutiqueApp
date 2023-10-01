package com.service.controller;

import com.homecode.commons.dto.CustomerDTO;
import com.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> findAll() {
        return this.customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable("id") Long id) {
        return this.customerService.findById(id);
    }

    @GetMapping("/active")
    public List<CustomerDTO> findAllActive() {
        return this.customerService.findAllActive();
    }
    @GetMapping("/inactive")
    public List<CustomerDTO> findAllInactive() {
        return this.customerService.findAllInactive();
    }
    @PostMapping("/create")
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO) {
      return this.customerService.create(customerDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.customerService.delete(id);
    }
}
