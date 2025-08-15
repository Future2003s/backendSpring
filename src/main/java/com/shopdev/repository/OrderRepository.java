package com.shopdev.repository;

import com.shopdev.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @EntityGraph(attributePaths = {"items"})
    @NonNull
    Page<OrderEntity> findAll(@NonNull Pageable pageable);
}


