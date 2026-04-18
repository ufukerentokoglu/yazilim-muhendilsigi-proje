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
            case "eskisehir" -> new IcAnadoluMainDish("Çibörek", "Eskişehir usulü kıymalı çibörek", 110, "Eskişehir");
            case "nevsehir" -> new IcAnadoluMainDish("Nevşehir Testi Kebabı", "Testi içinde pişen kebap", 190, "Nevşehir");
            case "aksaray" -> new IcAnadoluMainDish("Aksaray Bamya Çorbası", "Aksaray usulü bamya çorbası", 100, "Aksaray");
            case "nigde" -> new IcAnadoluMainDish("Niğde Köftesi", "Niğde usulü sulu köfte", 125, "Niğde");
            case "kirsehir" -> new IcAnadoluMainDish("Kırşehir Köftesi", "Kırşehir usulü ızgara köfte", 125, "Kırşehir");
            case "yozgat" -> new IcAnadoluMainDish("Yozgat Arabaşı", "Yozgat usulü arabaşı", 110, "Yozgat");
            case "karaman" -> new IcAnadoluMainDish("Karaman Etli Ekmek", "Karaman usulü etli ekmek", 135, "Karaman");
            case "kirikkale" -> new IcAnadoluMainDish("Kırıkkale Kebabı", "Kırıkkale usulü kebap", 140, "Kırıkkale");
            case "cankiri" -> new IcAnadoluMainDish("Çankırı Pastırması", "Çankırı usulü pastırma kavurma", 150, "Çankırı");
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
            case "eskisehir" -> new IcAnadoluAppetizer("Balaban Köfte", "Eskişehir usulü balaban köfte", 75, "Eskişehir");
            case "nevsehir" -> new IcAnadoluAppetizer("Arabaşı Çorbası", "Nevşehir usulü arabaşı", 55, "Nevşehir");
            case "aksaray" -> new IcAnadoluAppetizer("Tirit", "Aksaray usulü tirit", 60, "Aksaray");
            case "nigde" -> new IcAnadoluAppetizer("Soğan Çorbası", "Niğde soğan çorbası", 40, "Niğde");
            case "kirsehir" -> new IcAnadoluAppetizer("Kırşehir Mantısı", "Kırşehir usulü mantı", 65, "Kırşehir");
            case "yozgat" -> new IcAnadoluAppetizer("Yozgat Çorbası", "Yozgat usulü un çorbası", 40, "Yozgat");
            case "karaman" -> new IcAnadoluAppetizer("Karaman Çorbası", "Karaman usulü tarhana", 42, "Karaman");
            case "kirikkale" -> new IcAnadoluAppetizer("Kırıkkale Mantısı", "Kırıkkale usulü mantı", 55, "Kırıkkale");
            case "cankiri" -> new IcAnadoluAppetizer("Çankırı Çorbası", "Çankırı usulü lebeniye çorbası", 45, "Çankırı");
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
            case "eskisehir" -> new IcAnadoluDessert("Met Helvası", "Eskişehir geleneksel met helvası", 50, "Eskişehir");
            case "nevsehir" -> new IcAnadoluDessert("Pekmezli Tatlı", "Nevşehir pekmez tatlısı", 45, "Nevşehir");
            case "aksaray" -> new IcAnadoluDessert("Tahinli Pide", "Aksaray tahinli pide tatlısı", 50, "Aksaray");
            case "nigde" -> new IcAnadoluDessert("Niğde Elma Tatlısı", "Niğde elması ile tatlı", 55, "Niğde");
            case "kirsehir" -> new IcAnadoluDessert("Kırşehir Cevizli Sucuğu", "Cevizli sucuk tatlısı", 45, "Kırşehir");
            case "yozgat" -> new IcAnadoluDessert("Yozgat Pekmez Helvası", "Pekmezli helva", 45, "Yozgat");
            case "karaman" -> new IcAnadoluDessert("Divle Peyniri Tatlısı", "Divle peynirli tatlı", 55, "Karaman");
            case "kirikkale" -> new IcAnadoluDessert("Kırıkkale Un Helvası", "Geleneksel un helvası", 40, "Kırıkkale");
            case "cankiri" -> new IcAnadoluDessert("Çankırı Kristal Helvası", "Çankırı kristal un helvası", 50, "Çankırı");
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
            case "eskisehir" -> new IcAnadoluBeverage("Nuga", "Eskişehir usulü nuga içeceği", 25, "Eskişehir");
            case "nevsehir" -> new IcAnadoluBeverage("Üzüm Şırası", "Nevşehir üzüm şırası", 25, "Nevşehir");
            case "aksaray" -> new IcAnadoluBeverage("Ayran", "Aksaray köy ayranı", 18, "Aksaray");
            case "nigde" -> new IcAnadoluBeverage("Elma Çayı", "Niğde elma çayı", 20, "Niğde");
            case "kirsehir" -> new IcAnadoluBeverage("Kırşehir Ayranı", "Geleneksel ayran", 18, "Kırşehir");
            case "yozgat" -> new IcAnadoluBeverage("Yozgat Şırası", "Yozgat üzüm şırası", 20, "Yozgat");
            case "karaman" -> new IcAnadoluBeverage("Karaman Ayranı", "Karaman köy ayranı", 15, "Karaman");
            case "kirikkale" -> new IcAnadoluBeverage("Kırıkkale Çayı", "Geleneksel çay", 15, "Kırıkkale");
            case "cankiri" -> new IcAnadoluBeverage("Çankırı Ayranı", "Çankırı yayık ayranı", 18, "Çankırı");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "İç Anadolu"; }

    @Override
    public String getRegionKey() { return "ic_anadolu"; }

    @Override
    public List<String> getCities() { return List.of("ankara", "konya", "kayseri", "sivas", "eskisehir", "nevsehir", "aksaray", "nigde", "kirsehir", "yozgat", "karaman", "kirikkale", "cankiri"); }
}
