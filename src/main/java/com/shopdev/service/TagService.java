package com.shopdev.service;

import com.shopdev.dto.request.TagRequest;
import com.shopdev.model.TagEntity;

public interface TagService {
    TagEntity create(TagRequest request);

    java.util.List<TagEntity> findAll();
}


