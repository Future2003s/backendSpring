package com.shopdev.controller;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.BrandEntity;
import com.shopdev.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @PostMapping
    public ResponseData<BrandEntity> create(@RequestBody BrandRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Brand Successfully", brandService.create(request));
    }
}


