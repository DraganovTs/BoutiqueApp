package com.homecode.BoutiqueApp.service;

import com.homecode.BoutiqueApp.model.Cart;
import com.homecode.BoutiqueApp.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderService {
    public Order create(Cart cart) {
        return null;
    }
}
