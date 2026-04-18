package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.marmara.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarmaraFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "istanbul" -> new MarmaraMainDish("Kuru Fasulye", "Geleneksel İstanbul usulü kuru fasulye pilav", 120, "İstanbul");
            case "bursa" -> new MarmaraMainDish("İskender Kebap", "Bursa'nın meşhur döner kebabı yoğurt ve tereyağı ile", 250, "Bursa");
            case "edirne" -> new MarmaraMainDish("Tava Ciğeri", "Edirne usulü çıtır dana ciğer tava", 180, "Edirne");
            case "tekirdag" -> new MarmaraMainDish("Tekirdağ Köftesi", "Tekirdağ usulü ızgara köfte", 160, "Tekirdağ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "istanbul" -> new MarmaraAppetizer("Mercimek Çorbası", "Klasik Türk mercimek çorbası", 60, "İstanbul");
            case "bursa" -> new MarmaraAppetizer("Cantık", "Bursa'ya özgü kızartılmış hamur", 50, "Bursa");
            case "edirne" -> new MarmaraAppetizer("Edirne Peynir Helvası", "Beyaz peynirli geleneksel helva", 70, "Edirne");
            case "tekirdag" -> new MarmaraAppetizer("Sarımsak Çorbası", "Tekirdağ usulü sarımsaklı çorba", 55, "Tekirdağ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "istanbul" -> new MarmaraDessert("Kazandibi", "Karamelize sütlü tatlı", 80, "İstanbul");
            case "bursa" -> new MarmaraDessert("Kestane Şekeri", "Bursa kestane şekeri", 90, "Bursa");
            case "edirne" -> new MarmaraDessert("Badem Ezmesi", "Edirne'nin meşhur badem ezmesi", 75, "Edirne");
            case "tekirdag" -> new MarmaraDessert("İrmik Helvası", "Geleneksel irmik helvası", 65, "Tekirdağ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "istanbul" -> new MarmaraBeverage("Türk Kahvesi", "Geleneksel köpüklü Türk kahvesi", 45, "İstanbul");
            case "bursa" -> new MarmaraBeverage("Boza", "Geleneksel fermente içecek", 40, "Bursa");
            case "edirne" -> new MarmaraBeverage("Ayran", "Geleneksel yoğurt içeceği", 25, "Edirne");
            case "tekirdag" -> new MarmaraBeverage("Şalgam Suyu", "Ekşi şalgam suyu", 30, "Tekirdağ");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Marmara"; }

    @Override
    public String getRegionKey() { return "marmara"; }

    @Override
    public List<String> getCities() { return List.of("istanbul", "bursa", "edirne", "tekirdag"); }
}
