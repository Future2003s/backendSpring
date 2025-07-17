package com.shopdev.service.impl;

import com.shopdev.dto.request.ProductCreateRequest;
import com.shopdev.model.CategoryEntity;
import com.shopdev.model.ProductEntity;
import com.shopdev.repository.CategoryRepository;
import com.shopdev.repository.ProductRepository;
import com.shopdev.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Override
    public ProductEntity createProduct(ProductCreateRequest productCreateRequest) {
        CategoryEntity categoryEntity = categoryRepository.findById(
                productCreateRequest.getCategoryId()
        ).orElseThrow(() -> new RuntimeException("Category Not Found ID: " + productCreateRequest.getCategoryId()));

        ProductEntity newProduct = ProductEntity.builder()
                .productName(productCreateRequest.getProductName())
                .price(productCreateRequest.getPrice())
                .description(productCreateRequest.getDescription())
                .imageUrl(productCreateRequest.getImageUrl())
                .stockKeepingUnit(productCreateRequest.getStockKeepingUnit())
//               Mối Quan Hệ
                .category(categoryEntity)
                .build();

        return productRepository.save(newProduct);
    }
}
