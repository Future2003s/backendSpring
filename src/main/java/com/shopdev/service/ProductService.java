package com.shopdev.service;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.request.ProductUpdateRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.dto.response.ProductListItemResponse;
import com.shopdev.dto.response.ProductDetailResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    java.util.List<ProductListItemResponse> listProducts(String keyword, Long categoryId, String brandId, int page, int size);

    java.util.List<ProductListItemResponse> listProductsForAdmin(String keyword, Long categoryId, String brandId, String status, int page, int size);

    ProductDetailResponse getProduct(String productId);

    ProductResponse updateProduct(String id, ProductUpdateRequest request);

    void deleteProduct(String id);
}
