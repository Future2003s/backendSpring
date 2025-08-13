package com.shopdev.service.impl;

import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.model.CategoryEntity;
import com.shopdev.repository.CategoryRepository;
import com.shopdev.service.CategoryService;
import com.shopdev.dto.response.CategoryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(entity -> CategoryResponse.builder()
                        .id(entity.getId())
                        .categoryName(entity.getCategoryName())
                        .build())
                .toList();
    }
}
