package com.shopdev.controller;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.BrandEntity;
import com.shopdev.dto.response.BrandResponse;
import com.shopdev.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/v1/api/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<BrandEntity> create(@RequestBody BrandRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Brand Successfully", brandService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<List<BrandResponse>> list() {
        try {
            return new ResponseData<>(HttpStatus.OK, "OK", brandService.findAll());
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.OK, "OK", Collections.emptyList());
        }
    }

    @GetMapping("/public")
    public ResponseData<List<BrandResponse>> listPublic() {
        try {
            return new ResponseData<>(HttpStatus.OK, "OK", brandService.findAll());
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.OK, "OK", Collections.emptyList());
        }
    }
}


