package com.shopdev.service.impl;

import com.shopdev.dto.request.TagRequest;
import com.shopdev.model.TagEntity;
import com.shopdev.repository.TagRepository;
import com.shopdev.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Override
    public TagEntity create(TagRequest request) {
        TagEntity tag = TagEntity.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();
        return tagRepository.save(tag);
    }

    @Override
    public java.util.List<TagEntity> findAll() {
        return tagRepository.findAll();
    }
}


