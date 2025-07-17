package com.shopdev.service;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.model.ProductEntity;

public interface ProductService {
    ProductEntity createProduct(ProductRequest request);
}
