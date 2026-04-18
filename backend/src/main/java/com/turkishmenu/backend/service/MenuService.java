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

    public MenuService(FactoryProvider factoryProvider) {
        this.factoryProvider = factoryProvider;
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
                toDishDTO(mainDish, "Ana Yemek"),
                toDishDTO(appetizer, "Başlangıç"),
                toDishDTO(dessert, "Tatlı"),
                toDishDTO(beverage, "İçecek")
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

            dishes.add(toDishDTO(m, "Ana Yemek"));
            dishes.add(toDishDTO(a, "Başlangıç"));
            dishes.add(toDishDTO(d, "Tatlı"));
            dishes.add(toDishDTO(b, "İçecek"));
        }
        return dishes;
    }

    private DishDTO toDishDTO(MainDish dish, String category) {
        return new DishDTO(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getCity(), dish.getRegion(), category);
    }

    private DishDTO toDishDTO(Appetizer dish, String category) {
        return new DishDTO(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getCity(), dish.getRegion(), category);
    }

    private DishDTO toDishDTO(Dessert dish, String category) {
        return new DishDTO(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getCity(), dish.getRegion(), category);
    }

    private DishDTO toDishDTO(Beverage dish, String category) {
        return new DishDTO(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getCity(), dish.getRegion(), category);
    }
}
