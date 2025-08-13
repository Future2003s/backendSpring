package com.shopdev.controller;


import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.CategoryEntity;
import com.shopdev.dto.response.CategoryResponse;
import com.shopdev.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/create-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<CategoryEntity> createCategory(@RequestBody CategoryRequest request) {
        return new ResponseData<>(HttpStatus.CREATED,
                "Create Category SuccessFully", categoryService.createCategory(request));
    }

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<CategoryResponse>> listCategories() {
        try {
            return new ResponseData<>(HttpStatus.OK, "OK", categoryService.findAll());
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.OK, "OK", Collections.emptyList());
        }
    }
}
