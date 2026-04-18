package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.dto.MenuDTO;
import com.turkishmenu.backend.model.dto.RegionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    private final FactoryProvider factoryProvider;
    private final PriceService priceService;

    public MenuService(FactoryProvider factoryProvider, PriceService priceService) {
        this.factoryProvider = factoryProvider;
        this.priceService = priceService;
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

        MainDish mainDish = factory.createMainDish(city);
        Appetizer appetizer = factory.createAppetizer(city);
        Dessert dessert = factory.createDessert(city);
        Beverage beverage = factory.createBeverage(city);

        return new MenuDTO(
                factory.getRegionName(),
                city,
                toDishDTO(mainDish, "Ana Yemek", regionKey, city),
                toDishDTO(appetizer, "Başlangıç", regionKey, city),
                toDishDTO(dessert, "Tatlı", regionKey, city),
                toDishDTO(beverage, "İçecek", regionKey, city)
        );
    }

    public List<DishDTO> getAllDishes(String regionKey) {
        RegionFactory factory = factoryProvider.getFactory(regionKey);
        List<DishDTO> dishes = new ArrayList<>();

        for (String city : factory.getCities()) {
            MainDish m = factory.createMainDish(city);
            Appetizer a = factory.createAppetizer(city);
            Dessert d = factory.createDessert(city);
            Beverage b = factory.createBeverage(city);

            dishes.add(toDishDTO(m, "Ana Yemek", regionKey, city));
            dishes.add(toDishDTO(a, "Başlangıç", regionKey, city));
            dishes.add(toDishDTO(d, "Tatlı", regionKey, city));
            dishes.add(toDishDTO(b, "İçecek", regionKey, city));
        }
        return dishes;
    }

    private DishDTO toDishDTO(MainDish dish, String category, String regionKey, String city) {
        double price = priceService.getOverridePrice(regionKey, city, category).orElse(dish.getPrice());
        return new DishDTO(dish.getName(), dish.getDescription(), price, dish.getCity(), dish.getRegion(), category, dish.getPrepTime());
    }

    private DishDTO toDishDTO(Appetizer dish, String category, String regionKey, String city) {
        double price = priceService.getOverridePrice(regionKey, city, category).orElse(dish.getPrice());
        return new DishDTO(dish.getName(), dish.getDescription(), price, dish.getCity(), dish.getRegion(), category, dish.getPrepTime());
    }

    private DishDTO toDishDTO(Dessert dish, String category, String regionKey, String city) {
        double price = priceService.getOverridePrice(regionKey, city, category).orElse(dish.getPrice());
        return new DishDTO(dish.getName(), dish.getDescription(), price, dish.getCity(), dish.getRegion(), category, dish.getPrepTime());
    }

    private DishDTO toDishDTO(Beverage dish, String category, String regionKey, String city) {
        double price = priceService.getOverridePrice(regionKey, city, category).orElse(dish.getPrice());
        return new DishDTO(dish.getName(), dish.getDescription(), price, dish.getCity(), dish.getRegion(), category, dish.getPrepTime());
    }
}
