package com.homecode.product.service;

import com.homecode.commons.dto.CategoryDTO;
import com.homecode.product.model.Category;
import com.homecode.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        return this.categoryRepository.findAll()
                .stream()
                .map(CategoryService::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        log.debug("Request to get  Category by id {}", id);
        return this.categoryRepository.findById(id).map(CategoryService::mapToDTO).orElse(null);
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        log.debug("Request to create Category: {}", categoryDTO);
        return mapToDTO(this.categoryRepository.save(
                new Category(
                        categoryDTO.getName(),
                        categoryDTO.getDescription(),
                        Collections.emptySet())
        ));

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
