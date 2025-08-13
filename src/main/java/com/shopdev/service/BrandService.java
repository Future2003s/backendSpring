package com.shopdev.service;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.model.BrandEntity;
import com.shopdev.dto.response.BrandResponse;
import java.util.*;

public interface BrandService {
    BrandEntity create(BrandRequest request);

    List<BrandResponse> findAll();
}


