package com.shopdev.controller;


import com.shopdev.dto.request.ProductRequest;
import com.shopdev.dto.response.ProductListItemResponse;
import com.shopdev.dto.response.ProductResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.service.ProductService;
import com.shopdev.dto.request.ProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/api/products")
public class ProductController {
    ProductService productService;

    @PostMapping("/createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Produduct SuccessFully", productService.createProduct(request));
    }

    @GetMapping
    public ResponseData<java.util.List<ProductResponse>> placeholder() {
        return new ResponseData<>(HttpStatus.OK, "Use /public for customer listing", null);
    }


    @GetMapping("/public")
    public ResponseData<List<ProductListItemResponse>> listProducts(
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseData<List<ProductListItemResponse>> listProductsForAdmin(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) String brandId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return new ResponseData<>(HttpStatus.OK, "OK", productService.listProductsForAdmin(keyword, categoryId, brandId, status, page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseData<ProductResponse> updateProduct(@PathVariable("id") String id,
                                                       @Valid @RequestBody ProductUpdateRequest request) {
        return new ResponseData<>(HttpStatus.OK, "Updated", productService.updateProduct(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT, "Deleted", null);
    }
}
