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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseData<java.util.List<ProductResponse>> placeholder() {
        return new ResponseData<>(HttpStatus.OK, "Use /public for customer listing", null);
    }

    @GetMapping("/public")
    public ResponseData<java.util.List<com.shopdev.dto.response.ProductListItemResponse>> listProducts(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) String brandId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return new ResponseData<>(HttpStatus.OK, "OK", productService.listProducts(keyword, categoryId, brandId, page, size));
    }

    @GetMapping("/public/{id}")
    public ResponseData<com.shopdev.dto.response.ProductDetailResponse> getProduct(@PathVariable("id") String id) {
        return new ResponseData<>(HttpStatus.OK, "OK", productService.getProduct(id));
    }
}
