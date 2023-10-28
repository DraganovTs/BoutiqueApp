package com.homecode.product.controller;

import com.homecode.commons.module.dto.CategoryDTO;
import com.homecode.product.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.commons.module.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> findAll(){
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id){
        return this.categoryService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
        return this.categoryService.create(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
       return this.categoryService.delete(id);
    }
}
