package com.shopdev.controller;

import com.shopdev.dto.request.TagRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.TagEntity;
import com.shopdev.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping
    public ResponseData<TagEntity> create(@RequestBody TagRequest request) {
        return new ResponseData<>(HttpStatus.CREATED, "Create Tag Successfully", tagService.create(request));
    }
}


