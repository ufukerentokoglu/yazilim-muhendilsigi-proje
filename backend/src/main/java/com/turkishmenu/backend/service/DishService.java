package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.entity.DishEntity;
import com.turkishmenu.backend.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final FactoryProvider factoryProvider;

    public DishService(DishRepository dishRepository, FactoryProvider factoryProvider) {
        this.dishRepository = dishRepository;
        this.factoryProvider = factoryProvider;
    }

    public List<DishDTO> findByRegion(String regionKey) {
        return dishRepository.findByRegionKey(regionKey).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DishEntity> findEntitiesByRegion(String regionKey) {
        return dishRepository.findByRegionKey(regionKey);
    }

    public List<DishEntity> findByRegionAndCity(String regionKey, String city) {
        return dishRepository.findByRegionKeyAndCity(regionKey, city);
    }

    public Optional<DishEntity> findByRegionCityCategory(String regionKey, String city, String category) {
        return dishRepository.findFirstByRegionKeyAndCityAndCategoryOrderByIdDesc(regionKey, city, category);
    }

    public Optional<DishEntity> findById(Long id) {
        return dishRepository.findById(id);
    }

    @Transactional
    public DishEntity create(DishDTO dto, String regionKey) {
        RegionFactory factory = factoryProvider.getFactory(regionKey);
        DishEntity dish = new DishEntity(
                regionKey,
                factory.getRegionName(),
                dto.getCity(),
                dto.getCategory(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getPrepTime() > 0 ? dto.getPrepTime() : 20
        );
        return dishRepository.save(dish);
    }

    @Transactional
    public DishEntity update(Long id, DishDTO dto) {
        DishEntity dish = dishRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Yemek bulunamadı: " + id));
        if (dto.getName() != null) dish.setName(dto.getName());
        if (dto.getDescription() != null) dish.setDescription(dto.getDescription());
        if (dto.getPrice() > 0) dish.setPrice(dto.getPrice());
        if (dto.getCity() != null && !dto.getCity().isBlank()) dish.setCity(dto.getCity());
        if (dto.getCategory() != null && !dto.getCategory().isBlank()) dish.setCategory(dto.getCategory());
        if (dto.getPrepTime() > 0) dish.setPrepTime(dto.getPrepTime());
        return dishRepository.save(dish);
    }

    @Transactional
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    public DishDTO toDTO(DishEntity entity) {
        DishDTO dto = new DishDTO(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCity(),
                entity.getRegionName(),
                entity.getCategory(),
                entity.getPrepTime()
        );
        dto.setId(entity.getId());
        return dto;
    }
}
