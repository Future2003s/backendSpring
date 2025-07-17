package com.shopdev.controller;


import com.shopdev.dto.request.CategoryRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.CategoryEntity;
import com.shopdev.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseData<CategoryEntity> createCategory(@RequestBody CategoryRequest request) {
        return new ResponseData<>(HttpStatus.CREATED,
                "Create Category SuccessFully", categoryService.createCategory(request));
    }
}
