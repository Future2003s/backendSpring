package com.shopdev.repository;

import com.shopdev.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    @EntityGraph(attributePaths = {"brand", "category", "images"})
    @Query("select p from ProductEntity p " +
            "where (:keyword is null or lower(p.product_name) like lower(concat('%', :keyword, '%'))) " +
            "and (:categoryId is null or p.category.id = :categoryId) " +
            "and (:brandId is null or p.brand.id = :brandId)")
    Page<ProductEntity> searchList(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("brandId") String brandId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"brand", "category", "images"})
    @Query("select p from ProductEntity p " +
            "where (:keyword is null or lower(p.product_name) like lower(concat('%', :keyword, '%'))) " +
            "and (:categoryId is null or p.category.id = :categoryId) " +
            "and (:brandId is null or p.brand.id = :brandId) " +
            "and (:status is null or p.status = :status)")
    Page<ProductEntity> searchListForAdmin(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("brandId") String brandId,
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM ProductEntity p " +
           "LEFT JOIN FETCH p.brand " +
           "LEFT JOIN FETCH p.category " +
           "WHERE p.id = :id")
    Optional<ProductEntity> findWithAllById(@Param("id") String id);

    @Query("SELECT p.images FROM ProductEntity p WHERE p.id = :id")
    java.util.List<com.shopdev.model.ProductImage> findImagesByProductId(@Param("id") String id);
}
