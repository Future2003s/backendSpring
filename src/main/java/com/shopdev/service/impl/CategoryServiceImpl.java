package com.shopdev.service.impl;

import com.shopdev.model.CategoryEntity;
import com.shopdev.service.CategoryService;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return CategoryEntity.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
