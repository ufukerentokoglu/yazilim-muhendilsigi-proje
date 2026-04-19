package com.turkishmenu.backend.repository;

import com.turkishmenu.backend.model.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<DishEntity, Long> {
    List<DishEntity> findByRegionKey(String regionKey);
    List<DishEntity> findByRegionKeyAndCity(String regionKey, String city);
    Optional<DishEntity> findFirstByRegionKeyAndCityAndCategoryOrderByIdDesc(String regionKey, String city, String category);
}
