package com.shopdev.service;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.model.BrandEntity;

public interface BrandService {
    BrandEntity create(BrandRequest request);
}


