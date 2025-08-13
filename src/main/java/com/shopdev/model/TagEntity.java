package com.shopdev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "tbl_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagEntity extends AbstractEntity {
    @Column(name = "tag_name", nullable = false, length = 100)
    String name;

    @Column(name = "tag_slug", unique = true, length = 140)
    String slug;

    @ManyToMany(mappedBy = "tags")
    Set<ProductEntity> products;
}


