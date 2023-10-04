package com.homecode.product.controller;

import com.homecode.commons.dto.ProductDTO;
import com.homecode.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public List<ProductDTO> findAll(){
        return this.productService.findAll();
    }
    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable("id") Long id){
        return this.productService.findById(id);
    }

    @PostMapping()
    public ProductDTO create(@RequestBody ProductDTO productDTO){
      return this.productService.create(productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.productService.delete(id);
    }




}
