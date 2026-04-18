package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.doguanadolu.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoguAnadoluFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "erzurum" -> new DoguAnadoluMainDish("Cağ Kebabı", "Erzurum'un meşhur yatay şişte cağ kebabı", 200, "Erzurum");
            case "van" -> new DoguAnadoluMainDish("Van Kahvaltısı", "Dünyaca ünlü Van serpme kahvaltısı", 300, "Van");
            case "malatya" -> new DoguAnadoluMainDish("Kağıt Kebabı", "Malatya usulü kağıtta pişen kebap", 180, "Malatya");
            case "elazig" -> new DoguAnadoluMainDish("Harput Köftesi", "Elazığ Harput usulü içli köfte", 160, "Elazığ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "erzurum" -> new DoguAnadoluAppetizer("Su Böreği", "Erzurum usulü el açması su böreği", 70, "Erzurum");
            case "van" -> new DoguAnadoluAppetizer("Otlu Peynir", "Van otlu peyniri tabağı", 90, "Van");
            case "malatya" -> new DoguAnadoluAppetizer("Analı Kızlı", "Malatya'ya özgü köfteli çorba", 65, "Malatya");
            case "elazig" -> new DoguAnadoluAppetizer("Yayla Çorbası", "Elazığ usulü yoğurtlu yayla çorbası", 50, "Elazığ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "erzurum" -> new DoguAnadoluDessert("Kadayıf Dolması", "Erzurum usulü cevizli kadayıf", 85, "Erzurum");
            case "van" -> new DoguAnadoluDessert("Murtuğa", "Van'a özgü yumurtalı tatlı", 60, "Van");
            case "malatya" -> new DoguAnadoluDessert("Kayısı Tatlısı", "Malatya kayısısı ile tatlı", 75, "Malatya");
            case "elazig" -> new DoguAnadoluDessert("Gömbe", "Elazığ'ın geleneksel gömbe tatlısı", 55, "Elazığ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "erzurum" -> new DoguAnadoluBeverage("Ayran", "Erzurum köy ayranı", 20, "Erzurum");
            case "van" -> new DoguAnadoluBeverage("Van Otlu Çay", "Van otlarından bitki çayı", 25, "Van");
            case "malatya" -> new DoguAnadoluBeverage("Kayısı Suyu", "Malatya kayısısından taze meyve suyu", 30, "Malatya");
            case "elazig" -> new DoguAnadoluBeverage("Dut Şerbeti", "Elazığ'ın geleneksel dut şerbeti", 35, "Elazığ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Doğu Anadolu"; }

    @Override
    public String getRegionKey() { return "dogu_anadolu"; }

    @Override
    public List<String> getCities() { return List.of("erzurum", "van", "malatya", "elazig"); }
}
