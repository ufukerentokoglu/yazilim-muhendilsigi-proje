package com.turkishmenu.backend.repository;

import com.turkishmenu.backend.model.entity.PriceOverrideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceOverrideRepository extends JpaRepository<PriceOverrideEntity, Long> {
    Optional<PriceOverrideEntity> findByRegionKeyAndCityAndCategory(String regionKey, String city, String category);
}
