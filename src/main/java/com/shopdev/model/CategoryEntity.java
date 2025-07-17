package com.shopdev.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity extends AbstractEntity {
    @Column(name = "name_category", length = 50)
    String name;

    @Lob
    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    Set<ProductEntity> products = new HashSet<>();
}
