package com.turkishmenu.backend.config;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.model.entity.DishEntity;
import com.turkishmenu.backend.repository.DishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Uygulama ilk çalıştığında yemekler DB'de yoksa Abstract Factory üzerinden seed eder.
 * Factory'ler orijinal veri kaynağı olarak kalır; DB ise güncellenebilir depolama.
 */
@Component
public class DishSeeder implements CommandLineRunner {

    private final DishRepository dishRepository;
    private final FactoryProvider factoryProvider;

    public DishSeeder(DishRepository dishRepository, FactoryProvider factoryProvider) {
        this.dishRepository = dishRepository;
        this.factoryProvider = factoryProvider;
    }

    @Override
    public void run(String... args) {
        if (dishRepository.count() > 0) {
            return;
        }

        List<DishEntity> toSave = new ArrayList<>();
        for (RegionFactory factory : factoryProvider.getAllFactories()) {
            for (String cityKey : factory.getCities()) {
                MainDish m = factory.createMainDish(cityKey);
                Appetizer a = factory.createAppetizer(cityKey);
                Dessert d = factory.createDessert(cityKey);
                Beverage b = factory.createBeverage(cityKey);

                // city field'ı cityKey (küçük harf) olarak sakla — lookup tutarlılığı için
                toSave.add(new DishEntity(factory.getRegionKey(), factory.getRegionName(),
                        cityKey, "Ana Yemek", m.getName(), m.getDescription(), m.getPrice(), m.getPrepTime()));
                toSave.add(new DishEntity(factory.getRegionKey(), factory.getRegionName(),
                        cityKey, "Başlangıç", a.getName(), a.getDescription(), a.getPrice(), a.getPrepTime()));
                toSave.add(new DishEntity(factory.getRegionKey(), factory.getRegionName(),
                        cityKey, "Tatlı", d.getName(), d.getDescription(), d.getPrice(), d.getPrepTime()));
                toSave.add(new DishEntity(factory.getRegionKey(), factory.getRegionName(),
                        cityKey, "İçecek", b.getName(), b.getDescription(), b.getPrice(), b.getPrepTime()));
            }
        }

        dishRepository.saveAll(toSave);
        System.out.println("[DishSeeder] " + toSave.size() + " yemek DB'ye yüklendi.");
    }
}
