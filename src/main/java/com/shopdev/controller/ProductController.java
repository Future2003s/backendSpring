package com.shopdev.controller;


import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/api/products")
public class ProductController {
    ProductService productService;

    @PostMapping("/createProduct")
    public ResponseData<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Produduct SuccessFully", productService.createProduct(request));
    }
}
