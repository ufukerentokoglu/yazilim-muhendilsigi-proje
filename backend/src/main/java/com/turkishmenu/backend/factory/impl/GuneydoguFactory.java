package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.guneydogu.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuneydoguFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "gaziantep" -> new GuneydoguMainDish("Beyran", "Gaziantep'in meşhur beyran çorbası", 120, "Gaziantep");
            case "sanliurfa" -> new GuneydoguMainDish("Urfa Kebap", "Şanlıurfa'nın acısız kebabı", 180, "Şanlıurfa");
            case "diyarbakir" -> new GuneydoguMainDish("Kaburga Dolması", "Diyarbakır usulü pirinçli kaburga", 200, "Diyarbakır");
            case "mardin" -> new GuneydoguMainDish("Kaburga", "Mardin usulü kuzu kaburga", 190, "Mardin");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "gaziantep" -> new GuneydoguAppetizer("Lahmacun", "Gaziantep usulü ince lahmacun", 60, "Gaziantep");
            case "sanliurfa" -> new GuneydoguAppetizer("Çiğ Köfte", "Şanlıurfa'nın meşhur çiğ köftesi", 70, "Şanlıurfa");
            case "diyarbakir" -> new GuneydoguAppetizer("Diyarbakır Kahvaltısı", "Geleneksel Diyarbakır serpme kahvaltı", 150, "Diyarbakır");
            case "mardin" -> new GuneydoguAppetizer("İçli Köfte", "Mardin usulü bulgurlu içli köfte", 80, "Mardin");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "gaziantep" -> new GuneydoguDessert("Baklava", "Gaziantep'in dünyaca ünlü fıstıklı baklavası", 120, "Gaziantep");
            case "sanliurfa" -> new GuneydoguDessert("Şıllık", "Şanlıurfa'nın geleneksel şıllık tatlısı", 60, "Şanlıurfa");
            case "diyarbakir" -> new GuneydoguDessert("Kadayıf", "Diyarbakır usulü tel kadayıf", 90, "Diyarbakır");
            case "mardin" -> new GuneydoguDessert("Semirsek", "Mardin'e özgü semirsek tatlısı", 70, "Mardin");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "gaziantep" -> new GuneydoguBeverage("Menengiç Kahvesi", "Gaziantep'in meşhur menengiç kahvesi", 40, "Gaziantep");
            case "sanliurfa" -> new GuneydoguBeverage("Mırra", "Şanlıurfa'nın acı mırra kahvesi", 35, "Şanlıurfa");
            case "diyarbakir" -> new GuneydoguBeverage("Şıran Çayı", "Diyarbakır'ın geleneksel çayı", 20, "Diyarbakır");
            case "mardin" -> new GuneydoguBeverage("Mardin Mırrası", "Mardin usulü mırra kahvesi", 35, "Mardin");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Güneydoğu Anadolu"; }

    @Override
    public String getRegionKey() { return "guneydogu"; }

    @Override
    public List<String> getCities() { return List.of("gaziantep", "sanliurfa", "diyarbakir", "mardin"); }
}
