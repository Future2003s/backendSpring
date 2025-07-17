package com.shopdev.controller;


import com.shopdev.dto.request.ProductCreateRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.ProductEntity;
import com.shopdev.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createProduct")
    public ResponseData<ProductEntity> createProduct(@Valid ProductCreateRequest productCreateRequest) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Produduct SuccessFully", productService.createProduct(productCreateRequest));
    }
}
