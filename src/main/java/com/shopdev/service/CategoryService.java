package com.shopdev.service;

import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.model.CategoryEntity;

public interface CategoryService {
    CategoryEntity createCategory(CategoryRequest request);
}
