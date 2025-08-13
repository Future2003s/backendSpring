package com.shopdev.service.impl;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.model.BrandEntity;
import com.shopdev.repository.BrandRepository;
import com.shopdev.service.BrandService;
import com.shopdev.dto.response.BrandResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;

    @Override
    public BrandEntity create(BrandRequest request) {
        BrandEntity brand = BrandEntity.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(b -> BrandResponse.builder()
                        .id(b.getId())
                        .name(b.getName())
                        .slug(b.getSlug())
                        .build())
                .collect(java.util.stream.Collectors.toList());
    }
}


