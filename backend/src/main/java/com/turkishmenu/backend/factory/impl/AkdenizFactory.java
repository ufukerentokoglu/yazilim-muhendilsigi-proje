package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.akdeniz.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AkdenizFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "antalya" -> new AkdenizMainDish("Tandır Kebabı", "Antalya usulü kuzu tandır", 200, "Antalya");
            case "adana" -> new AkdenizMainDish("Adana Kebap", "Acılı el yapımı Adana kebabı", 220, "Adana");
            case "mersin" -> new AkdenizMainDish("Tantuni", "Mersin'in meşhur tantuni dürümü", 130, "Mersin");
            case "hatay" -> new AkdenizMainDish("Künefe", "Hatay'ın meşhur peynirli künefesi", 150, "Hatay");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "antalya" -> new AkdenizAppetizer("Piyaz", "Antalya usulü tahinli piyaz", 60, "Antalya");
            case "adana" -> new AkdenizAppetizer("Şalgam Çorbası", "Adana usulü şalgam çorbası", 55, "Adana");
            case "mersin" -> new AkdenizAppetizer("Cezerye", "Mersin'in havuçlu cezerye tatlısı", 70, "Mersin");
            case "hatay" -> new AkdenizAppetizer("Humus", "Hatay usulü nohut ezmesi", 65, "Hatay");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "antalya" -> new AkdenizDessert("Turunç Reçeli", "Antalya'nın meşhur turunç reçeli", 50, "Antalya");
            case "adana" -> new AkdenizDessert("Şırdan", "Adana'ya özgü geleneksel tatlı", 80, "Adana");
            case "mersin" -> new AkdenizDessert("Kerebiç", "Mersin'in meşhur kerebiç tatlısı", 75, "Mersin");
            case "hatay" -> new AkdenizDessert("Haytalya", "Hatay'ın sütlü haytalya tatlısı", 60, "Hatay");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "antalya" -> new AkdenizBeverage("Portakal Suyu", "Taze sıkılmış Antalya portakalı", 40, "Antalya");
            case "adana" -> new AkdenizBeverage("Şalgam Suyu", "Adana'nın meşhur şalgam suyu", 30, "Adana");
            case "mersin" -> new AkdenizBeverage("Limonata", "Mersin limonu ile ev yapımı limonata", 35, "Mersin");
            case "hatay" -> new AkdenizBeverage("Meyan Şerbeti", "Hatay'a özgü meyan kökü şerbeti", 35, "Hatay");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Akdeniz"; }

    @Override
    public String getRegionKey() { return "akdeniz"; }

    @Override
    public List<String> getCities() { return List.of("antalya", "adana", "mersin", "hatay"); }
}
