package com.homecode.customer.service;


import com.homecode.customer.model.dto.CustomerDTO;
import com.homecode.customer.model.entity.Customer;
import com.homecode.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        log.debug("Request to get all Customers");
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerService::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        log.debug("Request to get Customer by id {}", id);
        return this.customerRepository.findById(id)
                .map(CustomerService::mapToDTO).orElse(null);
    }
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllActive() {
        log.debug("Request to get all active Customers");
        return this.customerRepository.findAllByEnabled(true)
                .stream()
                .map(CustomerService::mapToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAllInactive() {
        log.debug("Request to get all inactive Customers");
        return this.customerRepository.findAllByEnabled(false)
                .stream()
                .map(CustomerService::mapToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO create(CustomerDTO customerDTO) {
        log.debug("Request to create Customer : {}" , customerDTO);
        return mapToDTO(
                this.customerRepository.save(
                        new Customer(
                                customerDTO.getFirstName(),
                                customerDTO.getLastName(),
                                customerDTO.getEmail(),
                                customerDTO.getTelephone(),
                                Collections.emptySet(),
                                Boolean.TRUE
                        )
                )
        );
    }

    public void delete(Long id) {
        log.debug("Request to delete Customer by id {}",id);
        this.customerRepository.deleteById(id);
    }

    public static CustomerDTO mapToDTO(Customer customer) {
        if (customer != null) {
            return new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getTelephone()
            );
        }
        return null;
    }
}
