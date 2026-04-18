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
            case "kars" -> new DoguAnadoluMainDish("Kars Kaz Eti", "Kars usulü kaz eti", 200, "Kars");
            case "bingol" -> new DoguAnadoluMainDish("Bingöl Köftesi", "Bingöl usulü ızgara köfte", 140, "Bingöl");
            case "erzincan" -> new DoguAnadoluMainDish("Erzincan Ekşili", "Erzincan usulü ekşili et", 155, "Erzincan");
            case "agri" -> new DoguAnadoluMainDish("Ağrı Köftesi", "Ağrı usulü ızgara köfte", 130, "Ağrı");
            case "igdir" -> new DoguAnadoluMainDish("Iğdır Köftesi", "Iğdır usulü köfte", 125, "Iğdır");
            case "mus" -> new DoguAnadoluMainDish("Muş Köftesi", "Muş usulü içli köfte", 135, "Muş");
            case "tunceli" -> new DoguAnadoluMainDish("Tunceli Kebabı", "Tunceli usulü kuzu kebabı", 160, "Tunceli");
            case "bitlis" -> new DoguAnadoluMainDish("Bitlis Köftesi", "Bitlis usulü içli köfte", 130, "Bitlis");
            case "hakkari" -> new DoguAnadoluMainDish("Hakkari Kebabı", "Hakkari usulü kuzu kebabı", 155, "Hakkari");
            case "ardahan" -> new DoguAnadoluMainDish("Ardahan Kaz Eti", "Ardahan usulü kaz eti", 180, "Ardahan");
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
            case "kars" -> new DoguAnadoluAppetizer("Kars Kaşarı Tabağı", "Kars eski kaşar tabağı", 85, "Kars");
            case "bingol" -> new DoguAnadoluAppetizer("Bingöl Çorbası", "Bingöl usulü yüksük çorbası", 45, "Bingöl");
            case "erzincan" -> new DoguAnadoluAppetizer("Erzincan Çeçil Peyniri", "Çeçil peynir tabağı", 70, "Erzincan");
            case "agri" -> new DoguAnadoluAppetizer("Ağrı Çorbası", "Ağrı usulü kesme çorba", 42, "Ağrı");
            case "igdir" -> new DoguAnadoluAppetizer("Iğdır Çorbası", "Iğdır usulü mercimek çorbası", 38, "Iğdır");
            case "mus" -> new DoguAnadoluAppetizer("Muş Çorbası", "Muş usulü buğday çorbası", 40, "Muş");
            case "tunceli" -> new DoguAnadoluAppetizer("Tunceli Çorbası", "Tunceli usulü kelle paça", 55, "Tunceli");
            case "bitlis" -> new DoguAnadoluAppetizer("Bitlis Çorbası", "Bitlis usulü süt çorbası", 42, "Bitlis");
            case "hakkari" -> new DoguAnadoluAppetizer("Hakkari Çorbası", "Hakkari usulü aş çorbası", 40, "Hakkari");
            case "ardahan" -> new DoguAnadoluAppetizer("Ardahan Çorbası", "Ardahan usulü kesme çorba", 45, "Ardahan");
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
            case "kars" -> new DoguAnadoluDessert("Kars Bal Kaymağı", "Kars balı ve kaymağı", 65, "Kars");
            case "bingol" -> new DoguAnadoluDessert("Bingöl Helvası", "Bingöl un helvası", 40, "Bingöl");
            case "erzincan" -> new DoguAnadoluDessert("Erzincan Tulum Peynir Tatlısı", "Tulum peynirli tatlı", 55, "Erzincan");
            case "agri" -> new DoguAnadoluDessert("Ağrı Bal Kaymak", "Ağrı bal kaymak tatlısı", 55, "Ağrı");
            case "igdir" -> new DoguAnadoluDessert("Iğdır Kayısı Tatlısı", "Iğdır kayısısı ile tatlı", 50, "Iğdır");
            case "mus" -> new DoguAnadoluDessert("Muş Helvası", "Muş un helvası", 42, "Muş");
            case "tunceli" -> new DoguAnadoluDessert("Tunceli Bal Helvası", "Tunceli bal helvası", 50, "Tunceli");
            case "bitlis" -> new DoguAnadoluDessert("Bitlis Büryan Tatlısı", "Bitlis geleneksel tatlısı", 48, "Bitlis");
            case "hakkari" -> new DoguAnadoluDessert("Hakkari Bal Peteği", "Hakkari dağ balı tatlısı", 60, "Hakkari");
            case "ardahan" -> new DoguAnadoluDessert("Ardahan Bal Kaymağı", "Ardahan bal kaymak tatlısı", 55, "Ardahan");
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
            case "kars" -> new DoguAnadoluBeverage("Kars Çayı", "Kars semaver çayı", 15, "Kars");
            case "bingol" -> new DoguAnadoluBeverage("Dağ Suyu", "Bingöl kaynak suyu", 10, "Bingöl");
            case "erzincan" -> new DoguAnadoluBeverage("Şıra", "Erzincan üzüm şırası", 25, "Erzincan");
            case "agri" -> new DoguAnadoluBeverage("Ağrı Ayranı", "Ağrı köy ayranı", 15, "Ağrı");
            case "igdir" -> new DoguAnadoluBeverage("Iğdır Kayısı Suyu", "Iğdır kayısı meyve suyu", 25, "Iğdır");
            case "mus" -> new DoguAnadoluBeverage("Muş Ayranı", "Muş yayık ayranı", 15, "Muş");
            case "tunceli" -> new DoguAnadoluBeverage("Tunceli Dağ Çayı", "Tunceli dağ bitkileri çayı", 18, "Tunceli");
            case "bitlis" -> new DoguAnadoluBeverage("Bitlis Ayranı", "Bitlis köy ayranı", 15, "Bitlis");
            case "hakkari" -> new DoguAnadoluBeverage("Hakkari Dağ Çayı", "Hakkari bitki çayı", 15, "Hakkari");
            case "ardahan" -> new DoguAnadoluBeverage("Ardahan Çayı", "Ardahan semaver çayı", 15, "Ardahan");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Doğu Anadolu"; }

    @Override
    public String getRegionKey() { return "dogu_anadolu"; }

    @Override
    public List<String> getCities() { return List.of("erzurum", "van", "malatya", "elazig", "kars", "bingol", "erzincan", "agri", "igdir", "mus", "tunceli", "bitlis", "hakkari", "ardahan"); }
}
