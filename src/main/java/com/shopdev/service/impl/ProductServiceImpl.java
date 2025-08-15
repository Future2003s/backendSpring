package com.shopdev.service.impl;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.dto.response.ProductListItemResponse;
import com.shopdev.dto.response.ProductDetailResponse;
import com.shopdev.dto.request.ProductUpdateRequest;
import com.shopdev.model.*;
import com.shopdev.enums.ProductStatus;
import com.shopdev.repository.*;
import com.shopdev.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

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
                .stockQuantity(request.getStockQuantity())
                .status(parseStatus(request.getStatus()))
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
        var pageable = org.springframework.data.domain.PageRequest.of(Math.max(page, 0), Math.max(size, 1));
        var pageData = productRepository.searchList(
                (keyword == null || keyword.isBlank()) ? null : keyword,
                categoryId,
                (brandId == null || brandId.isBlank()) ? null : brandId,
                pageable
        );
        
        return pageData.getContent().stream().map(p -> ProductListItemResponse.builder()
                .id(p.getId())
                .name(p.getProduct_name())
                .price(p.getPrice())
                .brandName(p.getBrand() != null ? p.getBrand().getName() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getCategoryName() : null)
                .imageUrls(p.getImages() != null ? p.getImages().stream()
                        .map(img -> img.getImageUrl())
                        .collect(java.util.stream.Collectors.toList()) : java.util.List.of())
                .stockQuantity(p.getStockQuantity())
                .status(p.getStatus() != null ? p.getStatus().name() : null)
                .build()).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ProductDetailResponse getProduct(String productId) {
        ProductEntity p = productRepository.findWithAllById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));
        
        // Fetch images separately to avoid MultipleBagFetchException
        List<String> imageUrls = java.util.List.of();
        try {
            var productImages = productRepository.findImagesByProductId(productId);
            if (productImages != null && !productImages.isEmpty()) {
                imageUrls = productImages.stream().map(ProductImage::getImageUrl).toList();
            }
        } catch (Exception e) {
            System.out.println("Error fetching images for product " + productId + ": " + e.getMessage());
            imageUrls = java.util.List.of();
        }
        
        // Fetch variants and tags separately to avoid MultipleBagFetchException
        List<ProductDetailResponse.Variant> variants = java.util.List.of();
        List<String> tags = java.util.List.of();
        
        if (p.getVariants() != null && !p.getVariants().isEmpty()) {
            variants = p.getVariants().stream().map(v -> ProductDetailResponse.Variant.builder()
                    .id(v.getId())
                    .sku(v.getSku())
                    .name(v.getName())
                    .price(v.getPrice())
                    .stockQuantity(v.getStockQuantity())
                    .build()).toList();
        }
        
        if (p.getTags() != null && !p.getTags().isEmpty()) {
            tags = p.getTags().stream().map(TagEntity::getName).toList();
        }
        
        return ProductDetailResponse.builder()
                .id(p.getId())
                .name(p.getProduct_name())
                .price(p.getPrice())
                .brandName(p.getBrand() != null ? p.getBrand().getName() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getCategoryName() : null)
                .imageUrls(imageUrls)
                .variants(variants)
                .tags(tags)
                .stockQuantity(p.getStockQuantity())
                .status(p.getStatus() != null ? p.getStatus().name() : null)
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

        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        
        if (request.getStatus() != null) {
            product.setStatus(parseStatus(request.getStatus()));
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

    private ProductStatus parseStatus(String input) {
        if (input == null) return null;
        try {
            return ProductStatus.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}