package com.homecode.BoutiqueApp.controller;

import com.homecode.BoutiqueApp.model.dto.ProductDTO;
import com.homecode.BoutiqueApp.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.BoutiqueApp.constants.Web.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
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
