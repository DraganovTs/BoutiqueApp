package com.homecode.product.service;


import com.homecode.commons.dto.ProductDTO;
import com.homecode.product.model.Product;
import com.homecode.product.model.enums.ProductStatus;
import com.homecode.product.repository.CategoryRepository;
import com.homecode.product.repository.ProductRepository;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        log.debug("Request to get all Products");
        return this.productRepository.findAll()
                .stream()
                .map(ProductService::mapToDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        log.debug("Request to get  Product by id {}",id);
        return this.productRepository.findById(id).map(ProductService::mapToDTO).orElse(null);
    }

    public ProductDTO create(ProductDTO productDTO) {
        log.debug("Request to create Product : {}", productDTO);
        return mapToDTO(this.productRepository.save(new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getQuantity(),
                ProductStatus.valueOf(productDTO.getProductStatus()),
                productDTO.getSalesCounter(),
                Collections.emptySet(),
                this.categoryRepository.findById(productDTO.getCategory().getId()).orElse(null))
        ));

    }

    public void delete(Long id) {
        log.debug("Request to delete Product by id {}",id);
        this.productRepository.deleteById(id);
    }

    public static ProductDTO mapToDTO(Product product) {
        if (product != null) {
            return new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getProductStatus().name(),
                    product.getSalesCounter(),
                    product.getReviews().stream().map(ReviewService::mapToDTO)
                            .collect(Collectors.toSet()),
                    CategoryService.mapToDTO(product.getCategory())
            );
        }
        return null;
    }
}
