package com.shopdev.controller;


import com.shopdev.model.CategoryEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class CategoryController {

    @PostMapping("/create-category")
    public CategoryEntity createCategory(@RequestBody CategoryEntity category) {
        
    }
}
