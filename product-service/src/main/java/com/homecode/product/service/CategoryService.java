package com.homecode.product.service;

import com.homecode.commons.module.dto.CategoryDTO;
import com.homecode.commons.module.exception.CustomAlreadyExistException;
import com.homecode.commons.module.exception.CustomDatabaseOperationException;
import com.homecode.commons.module.exception.CustomNotFoundException;
import com.homecode.commons.module.exception.CustomValidationException;
import com.homecode.product.model.Category;
import com.homecode.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<List<CategoryDTO>> findAll() {
        log.debug("Request to get all Categories");

        List<CategoryDTO> categories = this.categoryRepository.findAll()
                .stream()
                .map(CategoryService::mapToDTO)
                .collect(Collectors.toList());
        if (categories.isEmpty()) {
            throw new CustomNotFoundException("No categories available.",
                    "CATEGORIES_NOT_FOUND");
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CategoryDTO> findById(Long id) {
        log.debug("Request to get  Category by id {}", id);
        CategoryDTO category = this.categoryRepository.findById(id).map(CategoryService::mapToDTO)
                .orElse(null);
        if (category == null) {
            throw new CustomNotFoundException("Not found category whit id " + id,
                    "CATEGORY_NOT_FOUND");
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    public ResponseEntity<CategoryDTO> create(@Valid CategoryDTO categoryDTO) {

        log.debug("Request to create Category: {}", categoryDTO);

        if (this.categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new CustomAlreadyExistException("Category already exist",
                    "CATEGORY_EXIST");
        }
        try {
            Category category = mapToCategory(categoryDTO);

            this.categoryRepository.save(category);

            return new ResponseEntity<>(mapToDTO(category), HttpStatus.CREATED);

        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while creating category"
                    , "DATABASE_OPERATION_EXCEPTION");
        }

    }


    public ResponseEntity<?> delete(Long id) {
        log.debug("Request to delete Category by id {}", id);

        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new CustomNotFoundException("Not found category whit id " + id,
                    "CATEGORY_NOT_FOUND");
        }
        try {
            this.categoryRepository.deleteById(id);
            return new ResponseEntity<>("Delete category whit id " + id, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while deleting category with ID: " + id,
                    "DATABASE_OPERATION_EXCEPTION");
        }
    }

    public static CategoryDTO mapToDTO(Category category) {
        if (category == null) {
            throw new CustomValidationException("Not valid category",
                    "CATEGORY_NOT_VALID");
        }
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getProducts().
                        stream()
                        .map(ProductService::mapToDTO)
                        .collect(Collectors.toSet())
        );

    }

    private Category mapToCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new CustomValidationException("Not valid category",
                    "CATEGORY_NOT_VALID");
        }

        return new Category(
                categoryDTO.getName(),
                categoryDTO.getDescription(),
                new HashSet<>());


    }
}
