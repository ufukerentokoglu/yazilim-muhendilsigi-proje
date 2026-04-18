package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.icanadolu.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IcAnadoluFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "ankara" -> new IcAnadoluMainDish("Ankara Tavası", "Geleneksel Ankara tavası", 160, "Ankara");
            case "konya" -> new IcAnadoluMainDish("Etli Ekmek", "Konya'nın meşhur etli ekmeği", 140, "Konya");
            case "kayseri" -> new IcAnadoluMainDish("Mantı", "Kayseri'nin el yapımı mantısı", 130, "Kayseri");
            case "sivas" -> new IcAnadoluMainDish("Kangal Kebabı", "Sivas Kangal usulü kebap", 170, "Sivas");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "ankara" -> new IcAnadoluAppetizer("Ankara Simidi", "Çıtır Ankara simidi", 25, "Ankara");
            case "konya" -> new IcAnadoluAppetizer("Bamya Çorbası", "Konya usulü bamya çorbası", 55, "Konya");
            case "kayseri" -> new IcAnadoluAppetizer("Yağlama", "Kayseri'ye özgü yağlamalık", 60, "Kayseri");
            case "sivas" -> new IcAnadoluAppetizer("Madımak Çorbası", "Sivas'ın madımak otlu çorbası", 50, "Sivas");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "ankara" -> new IcAnadoluDessert("Kalburabastı", "Şerbetli geleneksel tatlı", 70, "Ankara");
            case "konya" -> new IcAnadoluDessert("Höşmerim", "Konya'nın peynirli tatlısı", 65, "Konya");
            case "kayseri" -> new IcAnadoluDessert("Pastırma Böreği", "Kayseri pastırmalı tatlı börek", 80, "Kayseri");
            case "sivas" -> new IcAnadoluDessert("Katmer", "Sivas usulü katmer tatlısı", 60, "Sivas");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "ankara" -> new IcAnadoluBeverage("Ayran", "Geleneksel köy ayranı", 20, "Ankara");
            case "konya" -> new IcAnadoluBeverage("Maden Suyu", "Konya maden suyu", 15, "Konya");
            case "kayseri" -> new IcAnadoluBeverage("Erciyes Çayı", "Kayseri dağ çayı", 25, "Kayseri");
            case "sivas" -> new IcAnadoluBeverage("Kefir", "Sivas'ın geleneksel kefiri", 30, "Sivas");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "İç Anadolu"; }

    @Override
    public String getRegionKey() { return "ic_anadolu"; }

    @Override
    public List<String> getCities() { return List.of("ankara", "konya", "kayseri", "sivas"); }
}
