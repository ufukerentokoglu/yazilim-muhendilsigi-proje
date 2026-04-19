package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.dto.MenuDTO;
import com.turkishmenu.backend.model.dto.RegionDTO;
import com.turkishmenu.backend.model.entity.DishEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final FactoryProvider factoryProvider;
    private final DishService dishService;

    public MenuService(FactoryProvider factoryProvider, DishService dishService) {
        this.factoryProvider = factoryProvider;
        this.dishService = dishService;
    }

    public List<RegionDTO> getAllRegions() {
        List<RegionDTO> regions = new ArrayList<>();
        for (RegionFactory factory : factoryProvider.getAllFactories()) {
            regions.add(new RegionDTO(factory.getRegionKey(), factory.getRegionName(), factory.getCities()));
        }
        return regions;
    }

    public MenuDTO getMenu(String regionKey, String city) {
        RegionFactory factory = factoryProvider.getFactory(regionKey);
        List<DishEntity> entities = dishService.findByRegionAndCity(regionKey, city);
        List<DishDTO> dishes = entities.stream().map(dishService::toDTO).collect(Collectors.toList());
        return new MenuDTO(factory.getRegionName(), city, dishes);
    }

    public List<DishDTO> getAllDishes(String regionKey) {
        return dishService.findByRegion(regionKey);
    }
}
