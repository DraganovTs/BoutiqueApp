package com.homecode.customer.feign;




import com.homecode.customer.model.dto.CartDTO;
import com.homecode.customer.model.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ORDER-SERVICE")
public interface OrderServiceInterface {
    @PostMapping("api/orders/createCart")
    OrderDTO create(@RequestBody CartDTO cartDTO);
}
