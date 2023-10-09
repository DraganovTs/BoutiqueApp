package com.homecode.product.service;


import com.homecode.commons.dto.ProductDTO;
import com.homecode.commons.exception.CustomAlreadyExistException;
import com.homecode.commons.exception.CustomDatabaseOperationException;
import com.homecode.commons.exception.CustomNotFoundException;
import com.homecode.commons.exception.CustomValidationException;
import com.homecode.product.model.Product;
import com.homecode.product.model.enums.ProductStatus;
import com.homecode.product.repository.CategoryRepository;
import com.homecode.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductDTO>> findAll() {
        log.debug("Request to get all Products");

        List<ProductDTO> products = this.productRepository.findAll()
                .stream()
                .map(ProductService::mapToDTO)
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new CustomNotFoundException("No products available.",
                    "PRODUCTS_NOT_FOUND");
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ProductDTO> findById(Long id) {
        log.debug("Request to get  Product by id {}", id);
        ProductDTO product = this.productRepository.findById(id).map(ProductService::mapToDTO).orElse(null);

        if (product == null) {
            throw new CustomNotFoundException("Not found product whit id " + id,
                    "PRODUCT_NOT_FOUND");
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<ProductDTO> create(ProductDTO productDTO) {
        log.debug("Request to create Product : {}", productDTO);

        if (this.productRepository.findByName(productDTO.getName()).isPresent()) {
            throw new CustomAlreadyExistException("Product already exist",
                    "PRODUCT_EXIST");
        }
        try {
            Product product = matToProduct(productDTO);

            this.productRepository.save(product);

        return new ResponseEntity<>(mapToDTO(product), HttpStatus.CREATED);

        } catch (Exception e) {
            throw new CustomDatabaseOperationException(e.getMessage(), "DATABASE_OPERATION_EXCEPTION");
        }

    }


    public void delete(Long id) {
        log.debug("Request to delete Product by id {}", id);
        try {
            this.productRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomNotFoundException("Not found product whit id " + id,
                    "PRODUCT_NOT_FOUND");
        }
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

    private Product matToProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new CustomValidationException("Not valid product",
                    "PRODUCT_NOT_VALID");
        }
        return new Product(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getQuantity(),
                ProductStatus.valueOf(productDTO.getProductStatus()),
                productDTO.getSalesCounter(),
                Collections.emptySet(),
                this.categoryRepository.findById(productDTO.getCategory().getId()).orElse(null));
    }
}
