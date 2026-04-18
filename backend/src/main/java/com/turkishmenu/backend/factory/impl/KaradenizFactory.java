package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.karadeniz.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KaradenizFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "trabzon" -> new KaradenizMainDish("Kuymak", "Trabzon usulü mısır unu ve peynirli kuymak", 120, "Trabzon");
            case "samsun" -> new KaradenizMainDish("Pideli Köfte", "Samsun'un meşhur pideli köftesi", 140, "Samsun");
            case "rize" -> new KaradenizMainDish("Hamsi Tava", "Rize usulü çıtır hamsi tava", 110, "Rize");
            case "giresun" -> new KaradenizMainDish("Giresun Pidesi", "Giresun usulü kapalı pide", 130, "Giresun");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "trabzon" -> new KaradenizAppetizer("Hamsi Pilavı", "Trabzon usulü hamsili pilav", 80, "Trabzon");
            case "samsun" -> new KaradenizAppetizer("Bafra Pidesi", "Samsun Bafra usulü pide", 90, "Samsun");
            case "rize" -> new KaradenizAppetizer("Muhlama", "Rize usulü peynirli muhlama", 75, "Rize");
            case "giresun" -> new KaradenizAppetizer("Mısır Çorbası", "Giresun usulü mısır çorbası", 50, "Giresun");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "trabzon" -> new KaradenizDessert("Laz Böreği", "Trabzon'un meşhur sütlü Laz böreği", 70, "Trabzon");
            case "samsun" -> new KaradenizDessert("Nokul", "Samsun'a özgü cevizli nokul", 55, "Samsun");
            case "rize" -> new KaradenizDessert("Laz Baklavası", "Rize usulü Laz baklavası", 80, "Rize");
            case "giresun" -> new KaradenizDessert("Fındık Baklavası", "Giresun fındığı ile baklava", 85, "Giresun");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "trabzon" -> new KaradenizBeverage("Trabzon Çayı", "Trabzon yaylalarından çay", 15, "Trabzon");
            case "samsun" -> new KaradenizBeverage("Çaykur Çayı", "Samsun demleme çayı", 15, "Samsun");
            case "rize" -> new KaradenizBeverage("Rize Çayı", "Rize'nin meşhur çayı", 12, "Rize");
            case "giresun" -> new KaradenizBeverage("Fındık Kahvesi", "Giresun fındığından kahve", 35, "Giresun");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Karadeniz"; }

    @Override
    public String getRegionKey() { return "karadeniz"; }

    @Override
    public List<String> getCities() { return List.of("trabzon", "samsun", "rize", "giresun"); }
}
