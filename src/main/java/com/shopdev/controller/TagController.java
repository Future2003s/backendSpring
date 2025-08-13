package com.shopdev.controller;

import com.shopdev.dto.request.TagRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.TagEntity;
import com.shopdev.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/v1/api/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseData<TagEntity> create(@RequestBody TagRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Tag Successfully", tagService.create(request));
    }

    @GetMapping
    public ResponseData<List<TagEntity>> list() {
        try {
            return new ResponseData<>(HttpStatus.OK, "OK", tagService.findAll());
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.OK, "OK", Collections.emptyList());
        }
    }
}


