package com.shopdev.service.impl;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.dto.response.ProductListItemResponse;
import com.shopdev.dto.response.ProductDetailResponse;
import com.shopdev.dto.request.ProductUpdateRequest;
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

    @Override
    public java.util.List<ProductListItemResponse> listProducts(String keyword, Long categoryId, String brandId, int page, int size) {
        java.util.List<ProductEntity> all = productRepository.findAll();
        java.util.List<ProductListItemResponse> result = new java.util.ArrayList<>();
        for (ProductEntity p : all) {
            if (keyword != null && !keyword.isBlank()) {
                if (p.getProduct_name() == null || !p.getProduct_name().toLowerCase().contains(keyword.toLowerCase())) continue;
            }
            if (categoryId != null && (p.getCategory() == null || !categoryId.equals(p.getCategory().getId()))) continue;
            if (brandId != null && (p.getBrand() == null || !brandId.equals(p.getBrand().getId()))) continue;

            result.add(ProductListItemResponse.builder()
                    .id(p.getId())
                    .name(p.getProduct_name())
                    .price(p.getPrice())
                    .brandName(p.getBrand() != null ? p.getBrand().getName() : null)
                    .categoryName(p.getCategory() != null ? p.getCategory().getCategoryName() : null)
                    .imageUrls(p.getImages() != null ? p.getImages().stream().map(ProductImage::getImageUrl).toList() : java.util.List.of())
                    .build());
        }
        return result;
    }

    @Override
    public ProductDetailResponse getProduct(String productId) {
        ProductEntity p = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));
        return ProductDetailResponse.builder()
                .id(p.getId())
                .name(p.getProduct_name())
                .price(p.getPrice())
                .brandName(p.getBrand() != null ? p.getBrand().getName() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getCategoryName() : null)
                .imageUrls(p.getImages() != null ? p.getImages().stream().map(ProductImage::getImageUrl).toList() : java.util.List.of())
                .variants(p.getVariants() != null ? p.getVariants().stream().map(v -> ProductDetailResponse.Variant.builder()
                        .id(v.getId())
                        .sku(v.getSku())
                        .name(v.getName())
                        .price(v.getPrice())
                        .stockQuantity(v.getStockQuantity())
                        .build()).toList() : java.util.List.of())
                .tags(p.getTags() != null ? p.getTags().stream().map(TagEntity::getName).toList() : java.util.List.of())
                .build();
    }

    @Override
    public ProductResponse updateProduct(String id, ProductUpdateRequest request) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        if (request.getProduct_name() != null) {
            product.setProduct_name(request.getProduct_name());
        }
        if (request.getProduct_price() != null) {
            product.setPrice(request.getProduct_price());
        }
        if (request.getCategory_id() != null) {
            CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category Not Found"));
            product.setCategory(category);
        }
        if (request.getBrand_id() != null) {
            BrandEntity brand = null;
            if (!request.getBrand_id().isBlank()) {
                brand = brandRepository.findById(request.getBrand_id())
                        .orElseThrow(() -> new RuntimeException("Brand Not Found"));
            }
            product.setBrand(brand);
        }
        if (request.getTag_ids() != null) {
            if (request.getTag_ids().isEmpty()) {
                product.setTags(new java.util.HashSet<>());
            } else {
                product.setTags(new java.util.HashSet<>(tagRepository.findAllById(request.getTag_ids())));
            }
        }
        if (request.getImage_urls() != null) {
            // Replace all images by mutating the managed collection to avoid orphanRemoval issues
            if (product.getImages() == null) {
                product.setImages(new java.util.ArrayList<>());
            } else {
                product.getImages().clear();
            }
            for (int i = 0; i < request.getImage_urls().size(); i++) {
                String url = request.getImage_urls().get(i);
                ProductImage image = ProductImage.builder()
                        .imageUrl(url)
                        .product(product)
                        .primary(i == 0)
                        .build();
                product.getImages().add(image);
            }
        }

        ProductEntity saved = productRepository.save(product);

        return ProductResponse.builder()
                .product_name(saved.getProduct_name())
                .product_price(saved.getPrice())
                .category_id(saved.getCategory() != null ? saved.getCategory().getId() : null)
                .build();
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product Not Found");
        }
        productRepository.deleteById(id);
    }
}