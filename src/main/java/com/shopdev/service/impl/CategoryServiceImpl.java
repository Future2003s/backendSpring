package com.shopdev.service.impl;

import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.model.CategoryEntity;
import com.shopdev.repository.CategoryRepository;
import com.shopdev.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryRequest request) {
        return categoryRepository.save(CategoryEntity.builder()
                .categoryName(request.getCategoryName())
                .build());
    }
}
