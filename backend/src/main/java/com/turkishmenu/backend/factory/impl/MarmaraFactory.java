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
            case "kocaeli" -> new MarmaraMainDish("Pişmaniye Kebabı", "Kocaeli usulü kebap", 150, "Kocaeli");
            case "balikesir" -> new MarmaraMainDish("Balıkesir Köftesi", "Balıkesir usulü köfte", 145, "Balıkesir");
            case "canakkale" -> new MarmaraMainDish("Çanakkale Sardalyası", "Taze ızgara sardalya", 130, "Çanakkale");
            case "yalova" -> new MarmaraMainDish("Yalova Köftesi", "Yalova usulü ızgara köfte", 135, "Yalova");
            case "sakarya" -> new MarmaraMainDish("Sakarya Islama Köfte", "Sakarya usulü ıslama köfte", 130, "Sakarya");
            case "bilecik" -> new MarmaraMainDish("Bilecik Köftesi", "Bilecik usulü köfte", 120, "Bilecik");
            case "kirklareli" -> new MarmaraMainDish("Kırklareli Köftesi", "Kırklareli usulü ızgara köfte", 135, "Kırklareli");
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
            case "kocaeli" -> new MarmaraAppetizer("İzmit Pidesi", "Kocaeli usulü pide", 65, "Kocaeli");
            case "balikesir" -> new MarmaraAppetizer("Höşmerim Çorbası", "Balıkesir usulü çorba", 50, "Balıkesir");
            case "canakkale" -> new MarmaraAppetizer("Peynir Tabağı", "Çanakkale ezine peyniri", 75, "Çanakkale");
            case "yalova" -> new MarmaraAppetizer("Termal Çorbası", "Yalova usulü sebze çorbası", 45, "Yalova");
            case "sakarya" -> new MarmaraAppetizer("Sakarya Pidesi", "Sakarya usulü pide", 60, "Sakarya");
            case "bilecik" -> new MarmaraAppetizer("Bilecik Çorbası", "Bilecik usulü tarhana çorbası", 40, "Bilecik");
            case "kirklareli" -> new MarmaraAppetizer("Hardaliye Salatası", "Kırklareli usulü hardaliye soslu salata", 55, "Kırklareli");
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
            case "kocaeli" -> new MarmaraDessert("Pişmaniye", "Kocaeli'nin meşhur pişmaniyesi", 45, "Kocaeli");
            case "balikesir" -> new MarmaraDessert("Peynir Helvası", "Balıkesir peynir helvası", 60, "Balıkesir");
            case "canakkale" -> new MarmaraDessert("Peynir Tatlısı", "Çanakkale peynir tatlısı", 55, "Çanakkale");
            case "yalova" -> new MarmaraDessert("Armut Tatlısı", "Yalova armutu ile tatlı", 50, "Yalova");
            case "sakarya" -> new MarmaraDessert("Sakarya Helvası", "Sakarya un helvası", 45, "Sakarya");
            case "bilecik" -> new MarmaraDessert("Pazarcık Helvası", "Bilecik geleneksel helvası", 50, "Bilecik");
            case "kirklareli" -> new MarmaraDessert("Kırklareli Badem Tatlısı", "Geleneksel badem tatlısı", 60, "Kırklareli");
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
            case "kocaeli" -> new MarmaraBeverage("Ayran", "Geleneksel köy ayranı", 20, "Kocaeli");
            case "balikesir" -> new MarmaraBeverage("Kestane Şerbeti", "Balıkesir kestane şerbeti", 35, "Balıkesir");
            case "canakkale" -> new MarmaraBeverage("Ada Çayı", "Çanakkale ada çayı", 25, "Çanakkale");
            case "yalova" -> new MarmaraBeverage("Ihlamur", "Yalova ıhlamur çayı", 20, "Yalova");
            case "sakarya" -> new MarmaraBeverage("Sakarya Çayı", "Sakarya demleme çayı", 15, "Sakarya");
            case "bilecik" -> new MarmaraBeverage("Ihlamur", "Bilecik ıhlamur çayı", 18, "Bilecik");
            case "kirklareli" -> new MarmaraBeverage("Hardaliye", "Kırklareli'nin meşhur hardaliyesi", 30, "Kırklareli");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Marmara"; }

    @Override
    public String getRegionKey() { return "marmara"; }

    @Override
    public List<String> getCities() { return List.of("istanbul", "bursa", "edirne", "tekirdag", "kocaeli", "balikesir", "canakkale", "yalova", "sakarya", "bilecik", "kirklareli"); }
}
