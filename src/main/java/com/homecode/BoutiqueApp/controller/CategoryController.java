package com.homecode.BoutiqueApp.controller;

import com.homecode.BoutiqueApp.model.dto.CategoryDTO;
import com.homecode.BoutiqueApp.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.BoutiqueApp.constants.Web.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> findAll(){
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable("id") Long id){
        return this.categoryService.findById(id);
    }

    @PostMapping("/create")
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO){
        return this.categoryService.create(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.categoryService.delete(id);
    }
}
