package com.homecode.BoutiqueApp.service;

import com.homecode.BoutiqueApp.model.Customer;
import com.homecode.BoutiqueApp.model.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CustomerService {











    public static CustomerDTO mapToDTO(Customer customer) {
        if (customer != null){
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
