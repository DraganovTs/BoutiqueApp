package com.homecode.product.service;

import com.homecode.commons.dto.CategoryDTO;
import com.homecode.product.exception.CategoryAlreadyInDataBaseException;
import com.homecode.product.exception.CustomValidationException;
import com.homecode.product.exception.CustomValiidationException;
import com.homecode.product.exception.NoDataFoundException;
import com.homecode.product.model.Category;
import com.homecode.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
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
            throw new NoDataFoundException("No categories available.",
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
            throw new NoDataFoundException("No category whit id " + id,
                    "CATEGORY_NOT_FOUND");
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    public ResponseEntity<CategoryDTO> create(@Valid CategoryDTO categoryDTO) {
        log.debug("Request to create Category: {}", categoryDTO);
        if (categoryDTO == null) {
            throw new CustomValidationException();
        }
        if (this.categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new CategoryAlreadyInDataBaseException();
        }
        try {
            Category category = Category.builder()
                    .name(categoryDTO.getName())
                    .description(categoryDTO.getDescription())
                    .build();

            this.categoryRepository.save(category);

            return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new CustomDatabaseOperationException();
        }

    }

    public void delete(Long id) {
        log.debug("Request to delete Category by id {}", id);
        this.categoryRepository.deleteById(id);
    }

    public static CategoryDTO mapToDTO(Category category) {
        if (category != null) {
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
        return null;
    }
}
