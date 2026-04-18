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
            case "adiyaman" -> new GuneydoguMainDish("Adıyaman Çiğ Köftesi", "Adıyaman usulü çiğ köfte", 110, "Adıyaman");
            case "batman" -> new GuneydoguMainDish("Batman Köftesi", "Batman usulü ızgara köfte", 130, "Batman");
            case "siirt" -> new GuneydoguMainDish("Siirt Büryan", "Siirt'in meşhur büryan kebabı", 195, "Siirt");
            case "sirnak" -> new GuneydoguMainDish("Şırnak Kebabı", "Şırnak usulü kuzu kebabı", 150, "Şırnak");
            case "kilis" -> new GuneydoguMainDish("Kilis Tava", "Kilis usulü et tava", 135, "Kilis");
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
            case "adiyaman" -> new GuneydoguAppetizer("Adıyaman Çorbası", "Adıyaman usulü mercimek çorbası", 45, "Adıyaman");
            case "batman" -> new GuneydoguAppetizer("Batman Çorbası", "Batman usulü ezogelin çorbası", 40, "Batman");
            case "siirt" -> new GuneydoguAppetizer("Perde Pilavı", "Siirt usulü perde pilavı", 80, "Siirt");
            case "sirnak" -> new GuneydoguAppetizer("Şırnak Çorbası", "Şırnak usulü mercimek çorbası", 42, "Şırnak");
            case "kilis" -> new GuneydoguAppetizer("Kilis Çorbası", "Kilis usulü ekşili çorba", 45, "Kilis");
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
            case "adiyaman" -> new GuneydoguDessert("Besni Tatlısı", "Adıyaman besni tatlısı", 50, "Adıyaman");
            case "batman" -> new GuneydoguDessert("Dobo Tatlısı", "Batman dobo tatlısı", 55, "Batman");
            case "siirt" -> new GuneydoguDessert("Siirt Bıttım Şekeri", "Bıttım fıstıklı şeker", 60, "Siirt");
            case "sirnak" -> new GuneydoguDessert("Şırnak Helvası", "Şırnak geleneksel helvası", 48, "Şırnak");
            case "kilis" -> new GuneydoguDessert("Kilis Şöbiyet", "Kilis usulü şöbiyet", 55, "Kilis");
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
            case "adiyaman" -> new GuneydoguBeverage("Adıyaman Şalgam", "Adıyaman şalgam suyu", 25, "Adıyaman");
            case "batman" -> new GuneydoguBeverage("Reyhan Şerbeti", "Batman reyhan şerbeti", 25, "Batman");
            case "siirt" -> new GuneydoguBeverage("Meyan Şerbeti", "Siirt meyan kökü şerbeti", 25, "Siirt");
            case "sirnak" -> new GuneydoguBeverage("Şırnak Çayı", "Şırnak demleme çayı", 18, "Şırnak");
            case "kilis" -> new GuneydoguBeverage("Kilis Kahvesi", "Kilis geleneksel kahvesi", 30, "Kilis");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Güneydoğu Anadolu"; }

    @Override
    public String getRegionKey() { return "guneydogu"; }

    @Override
    public List<String> getCities() { return List.of("gaziantep", "sanliurfa", "diyarbakir", "mardin", "adiyaman", "batman", "siirt", "sirnak", "kilis"); }
}
