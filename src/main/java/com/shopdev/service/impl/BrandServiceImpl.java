package com.shopdev.service.impl;

import com.shopdev.dto.request.BrandRequest;
import com.shopdev.model.BrandEntity;
import com.shopdev.repository.BrandRepository;
import com.shopdev.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
}


