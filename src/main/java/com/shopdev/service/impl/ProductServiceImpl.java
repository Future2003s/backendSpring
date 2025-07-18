package com.shopdev.service.impl;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.model.CategoryEntity;
import com.shopdev.model.ProductEntity;
import com.shopdev.repository.CategoryRepository;
import com.shopdev.repository.ProductRepository;
import com.shopdev.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategory_id()).orElseThrow(() -> new RuntimeException("Category Not Found"));

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .product_name(request.getProduct_name())
                        .price(request.getProduct_price())
                        .category(categoryEntity)
                        .build()
        );

        return ProductResponse.builder()
                .product_name(product.getProduct_name())
                .product_price(product.getPrice())
                .category_id(categoryEntity.getId())
                .category_id(categoryEntity.getId())
                .build();

    }
}