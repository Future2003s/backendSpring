package com.shopdev.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "tbl_brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandEntity extends AbstractEntity {

    @Column(name = "brand_name", nullable = false, length = 120)
    String name;

    @Column(name = "brand_slug", unique = true, length = 160)
    String slug;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    List<ProductEntity> products;
}


