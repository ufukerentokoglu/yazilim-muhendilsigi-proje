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
            case "isparta" -> new AkdenizMainDish("Isparta Kebabı", "Isparta usulü kuzu kebabı", 165, "Isparta");
            case "burdur" -> new AkdenizMainDish("Burdur Şiş Köfte", "Burdur usulü şişte köfte", 140, "Burdur");
            case "kahramanmaras" -> new AkdenizMainDish("Maraş Tarhanası", "Kahramanmaraş usulü tarhana", 120, "Kahramanmaraş");
            case "osmaniye" -> new AkdenizMainDish("Osmaniye Tava", "Osmaniye usulü et tava", 140, "Osmaniye");
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
            case "isparta" -> new AkdenizAppetizer("Gül Çorbası", "Isparta gül yapraklı çorba", 55, "Isparta");
            case "burdur" -> new AkdenizAppetizer("Ekşili Çorba", "Burdur usulü ekşili çorba", 45, "Burdur");
            case "kahramanmaras" -> new AkdenizAppetizer("Eli Böğründe", "Maraş usulü köfteli çorba", 65, "Kahramanmaraş");
            case "osmaniye" -> new AkdenizAppetizer("Osmaniye Çorbası", "Osmaniye usulü analı kızlı çorba", 50, "Osmaniye");
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
            case "isparta" -> new AkdenizDessert("Gül Lokumu", "Isparta gül aromalı lokum", 50, "Isparta");
            case "burdur" -> new AkdenizDessert("Burdur Şeker Böreği", "Şerbetli şeker böreği", 55, "Burdur");
            case "kahramanmaras" -> new AkdenizDessert("Maraş Dondurması", "Meşhur Maraş dondurması", 70, "Kahramanmaraş");
            case "osmaniye" -> new AkdenizDessert("Osmaniye Helvası", "Osmaniye geleneksel helvası", 45, "Osmaniye");
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
            case "isparta" -> new AkdenizBeverage("Gül Şerbeti", "Isparta gül şerbeti", 30, "Isparta");
            case "burdur" -> new AkdenizBeverage("Dağ Çayı", "Burdur dağ çayı", 20, "Burdur");
            case "kahramanmaras" -> new AkdenizBeverage("Maraş Kahvesi", "Geleneksel Maraş kahvesi", 35, "Kahramanmaraş");
            case "osmaniye" -> new AkdenizBeverage("Osmaniye Ayranı", "Osmaniye köy ayranı", 18, "Osmaniye");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Akdeniz"; }

    @Override
    public String getRegionKey() { return "akdeniz"; }

    @Override
    public List<String> getCities() { return List.of("antalya", "adana", "mersin", "hatay", "isparta", "burdur", "kahramanmaras", "osmaniye"); }
}
