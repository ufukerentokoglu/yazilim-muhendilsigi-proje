package com.turkishmenu.backend.service;

import com.turkishmenu.backend.model.entity.PriceOverrideEntity;
import com.turkishmenu.backend.repository.PriceOverrideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceOverrideRepository repository;

    public PriceService(PriceOverrideRepository repository) {
        this.repository = repository;
    }

    public Optional<Double> getOverridePrice(String regionKey, String city, String category) {
        return repository.findByRegionKeyAndCityAndCategory(regionKey, city, category)
                .map(PriceOverrideEntity::getNewPrice);
    }

    @Transactional
    public PriceOverrideEntity setPrice(String regionKey, String city, String category, double newPrice) {
        Optional<PriceOverrideEntity> existing = repository.findByRegionKeyAndCityAndCategory(regionKey, city, category);
        if (existing.isPresent()) {
            existing.get().setNewPrice(newPrice);
            return repository.save(existing.get());
        }
        return repository.save(new PriceOverrideEntity(regionKey, city, category, newPrice));
    }

    @Transactional
    public void resetPrice(String regionKey, String city, String category) {
        repository.findByRegionKeyAndCityAndCategory(regionKey, city, category)
                .ifPresent(repository::delete);
    }

    public List<PriceOverrideEntity> getAllOverrides() {
        return repository.findAll();
    }
}
