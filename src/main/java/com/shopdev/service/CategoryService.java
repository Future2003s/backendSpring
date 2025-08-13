package com.shopdev.service;

import java.util.List;

import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.model.CategoryEntity;
import com.shopdev.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryEntity createCategory(CategoryRequest request);

    List<CategoryResponse> findAll();
}
