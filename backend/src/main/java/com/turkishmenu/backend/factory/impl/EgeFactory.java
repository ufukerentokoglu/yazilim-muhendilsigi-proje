package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.factory.product.impl.ege.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EgeFactory extends RegionFactory {

    @Override
    public MainDish createMainDish(String city) {
        return switch (city.toLowerCase()) {
            case "izmir" -> new EgeMainDish("İzmir Köfte", "Patates ile fırında pişen İzmir köftesi", 150, "İzmir");
            case "mugla" -> new EgeMainDish("Muğla Kebabı", "Muğla usulü kuzu kebabı", 180, "Muğla");
            case "denizli" -> new EgeMainDish("Denizli Kebabı", "Denizli'ye özgü et kebabı", 170, "Denizli");
            case "aydin" -> new EgeMainDish("Çintar Kebabı", "Aydın'ın meşhur mantar kebabı", 140, "Aydın");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Appetizer createAppetizer(String city) {
        return switch (city.toLowerCase()) {
            case "izmir" -> new EgeAppetizer("Boyoz", "İzmir'in meşhur hamur işi", 35, "İzmir");
            case "mugla" -> new EgeAppetizer("Kabak Çiçeği Dolması", "Zeytinyağlı kabak çiçeği dolması", 65, "Muğla");
            case "denizli" -> new EgeAppetizer("Keledoş Çorbası", "Denizli'ye özgü buğday çorbası", 50, "Denizli");
            case "aydin" -> new EgeAppetizer("Pazi Sarma", "Aydın usulü pazi yaprağı sarması", 55, "Aydın");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Dessert createDessert(String city) {
        return switch (city.toLowerCase()) {
            case "izmir" -> new EgeDessert("Lokma", "İzmir usulü şerbetli lokma tatlısı", 50, "İzmir");
            case "mugla" -> new EgeDessert("İncir Tatlısı", "Muğla'nın taze incir tatlısı", 70, "Muğla");
            case "denizli" -> new EgeDessert("Horozbibi Tatlısı", "Denizli'ye özgü geleneksel tatlı", 60, "Denizli");
            case "aydin" -> new EgeDessert("Kestane Şükrası", "Aydın usulü kestane tatlısı", 75, "Aydın");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public Beverage createBeverage(String city) {
        return switch (city.toLowerCase()) {
            case "izmir" -> new EgeBeverage("Kumquat Likörü", "İzmir'e özgü kamkat meyvesi likörü", 55, "İzmir");
            case "mugla" -> new EgeBeverage("Ada Çayı", "Muğla dağlarından ada çayı", 30, "Muğla");
            case "denizli" -> new EgeBeverage("Üzüm Suyu", "Denizli üzümünden taze sıkma", 35, "Denizli");
            case "aydin" -> new EgeBeverage("Zeytin Yaprağı Çayı", "Aydın'a özgü zeytin yaprağı çayı", 30, "Aydın");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Ege"; }

    @Override
    public String getRegionKey() { return "ege"; }

    @Override
    public List<String> getCities() { return List.of("izmir", "mugla", "denizli", "aydin"); }
}
