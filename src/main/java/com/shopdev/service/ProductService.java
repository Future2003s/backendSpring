package com.shopdev.service;

import com.shopdev.dto.request.ProductCreateRequest;
import com.shopdev.model.ProductEntity;

public interface ProductService {
    ProductEntity createProduct(ProductCreateRequest productCreateRequest);
}
