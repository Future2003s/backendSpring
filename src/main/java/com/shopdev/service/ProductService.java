package com.shopdev.service;

import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
}
