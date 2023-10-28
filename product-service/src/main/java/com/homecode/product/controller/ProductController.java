package com.homecode.product.controller;

import com.homecode.commons.module.dto.ProductDTO;
import com.homecode.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.module.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> findAll(){
        return this.productService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id){
        return this.productService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO){
      return this.productService.create(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
       return this.productService.delete(id);
    }




}
