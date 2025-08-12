package com.shopdev.service.impl;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.model.*;
import com.shopdev.repository.*;
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
    BrandRepository brandRepository;
    TagRepository tagRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategory_id()).orElseThrow(() -> new RuntimeException("Category Not Found"));

        BrandEntity brand = null;
        if (request.getBrand_id() != null) {
            brand = brandRepository.findById(request.getBrand_id()).orElseThrow(() -> new RuntimeException("Brand Not Found"));
        }

        ProductEntity product = ProductEntity.builder()
                .product_name(request.getProduct_name())
                .price(request.getProduct_price())
                .category(categoryEntity)
                .brand(brand)
                .build();

        // Persist product first to obtain managed entity
        final ProductEntity savedProduct = productRepository.save(product);

        // Images
        if (request.getImage_urls() != null) {
            if (savedProduct.getImages() == null) {
                savedProduct.setImages(new java.util.ArrayList<>());
            }
            for (int i = 0; i < request.getImage_urls().size(); i++) {
                String url = request.getImage_urls().get(i);
                ProductImage image = ProductImage.builder()
                        .imageUrl(url)
                        .product(savedProduct)
                        .primary(i == 0)
                        .build();
                // Using cascade on product.images will save when added
                savedProduct.getImages().add(image);
            }
        }

        // Variants
        if (request.getVariants() != null) {
            if (savedProduct.getVariants() == null) {
                savedProduct.setVariants(new java.util.ArrayList<>());
            }
            request.getVariants().forEach(v -> {
                ProductVariant variant = ProductVariant.builder()
                        .product(savedProduct)
                        .sku(v.getSku())
                        .name(v.getName())
                        .price(v.getPrice())
                        .stockQuantity(v.getStockQuantity())
                        .build();
                savedProduct.getVariants().add(variant);
            });
        }

        // Tags
        if (request.getTag_ids() != null && !request.getTag_ids().isEmpty()) {
            savedProduct.setTags(new java.util.HashSet<>(tagRepository.findAllById(request.getTag_ids())));
        }

        // Save updates to child collections
        productRepository.save(savedProduct);

        return ProductResponse.builder()
                .product_name(savedProduct.getProduct_name())
                .product_price(savedProduct.getPrice())
                .category_id(categoryEntity.getId())
                .build();

    }
}