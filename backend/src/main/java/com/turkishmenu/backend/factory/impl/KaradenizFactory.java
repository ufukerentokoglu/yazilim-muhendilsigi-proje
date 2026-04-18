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
            case "ordu" -> new KaradenizMainDish("Ordu Pidesi", "Ordu usulü fındıklı pide", 120, "Ordu");
            case "artvin" -> new KaradenizMainDish("Artvin Kömbe", "Artvin usulü kömbe böreği", 100, "Artvin");
            case "sinop" -> new KaradenizMainDish("Sinop Nokulu", "Sinop usulü cevizli nokul", 90, "Sinop");
            case "bolu" -> new KaradenizMainDish("Bolu Kebabı", "Bolu usulü kuzu kebabı", 180, "Bolu");
            case "amasya" -> new KaradenizMainDish("Amasya Çökelekli Börek", "Amasya usulü çökelekli börek", 100, "Amasya");
            case "tokat" -> new KaradenizMainDish("Tokat Kebabı", "Tokat usulü patlıcanlı kebap", 155, "Tokat");
            case "duzce" -> new KaradenizMainDish("Düzce Pidesi", "Düzce usulü kapalı pide", 110, "Düzce");
            case "zonguldak" -> new KaradenizMainDish("Zonguldak Pidesi", "Zonguldak usulü pide", 105, "Zonguldak");
            case "bartin" -> new KaradenizMainDish("Bartın Pidesi", "Bartın usulü kapalı pide", 100, "Bartın");
            case "kastamonu" -> new KaradenizMainDish("Kastamonu Banduma", "Kastamonu usulü banduma", 120, "Kastamonu");
            case "corum" -> new KaradenizMainDish("Çorum Leblebi Kebabı", "Çorum usulü leblebi kebabı", 130, "Çorum");
            case "bayburt" -> new KaradenizMainDish("Bayburt Çöreği", "Bayburt usulü et çöreği", 115, "Bayburt");
            case "gumushane" -> new KaradenizMainDish("Gümüşhane Köftesi", "Gümüşhane usulü ızgara köfte", 125, "Gümüşhane");
            case "karabuk" -> new KaradenizMainDish("Karabük Etli Ekmek", "Karabük usulü etli ekmek", 115, "Karabük");
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
            case "ordu" -> new KaradenizAppetizer("Fındık Çorbası", "Ordu fındık çorbası", 55, "Ordu");
            case "artvin" -> new KaradenizAppetizer("Karalahana Sarması", "Artvin usulü lahana sarması", 65, "Artvin");
            case "sinop" -> new KaradenizAppetizer("Sinop Mantısı", "Sinop usulü mantı", 75, "Sinop");
            case "bolu" -> new KaradenizAppetizer("Abant Çorbası", "Bolu usulü mantar çorbası", 60, "Bolu");
            case "amasya" -> new KaradenizAppetizer("Amasya Çorbası", "Amasya usulü toyga çorbası", 45, "Amasya");
            case "tokat" -> new KaradenizAppetizer("Tokat Çorbası", "Tokat usulü batırık çorbası", 48, "Tokat");
            case "duzce" -> new KaradenizAppetizer("Düzce Mantısı", "Düzce usulü mantı", 65, "Düzce");
            case "zonguldak" -> new KaradenizAppetizer("Zonguldak Çorbası", "Zonguldak usulü lahana çorbası", 42, "Zonguldak");
            case "bartin" -> new KaradenizAppetizer("Bartın Çorbası", "Bartın usulü ıspanak çorbası", 40, "Bartın");
            case "kastamonu" -> new KaradenizAppetizer("Kastamonu Çorbası", "Kastamonu usulü etli çorba", 50, "Kastamonu");
            case "corum" -> new KaradenizAppetizer("Çorum İncesu Çorbası", "Çorum usulü sulu köfte", 50, "Çorum");
            case "bayburt" -> new KaradenizAppetizer("Bayburt Çorbası", "Bayburt usulü hingel çorbası", 48, "Bayburt");
            case "gumushane" -> new KaradenizAppetizer("Gümüşhane Siron", "Gümüşhane usulü siron", 60, "Gümüşhane");
            case "karabuk" -> new KaradenizAppetizer("Karabük Çorbası", "Karabük usulü un çorbası", 42, "Karabük");
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
            case "ordu" -> new KaradenizDessert("Fındıklı Baklava", "Ordu fındığı ile baklava", 80, "Ordu");
            case "artvin" -> new KaradenizDessert("Artvin Balı", "Artvin yaylası bal tatlısı", 70, "Artvin");
            case "sinop" -> new KaradenizDessert("Boy Baklavası", "Sinop usulü baklava", 65, "Sinop");
            case "bolu" -> new KaradenizDessert("Bolu Helvası", "Bolu geleneksel helvası", 55, "Bolu");
            case "amasya" -> new KaradenizDessert("Amasya Elma Tatlısı", "Amasya elması ile tatlı", 55, "Amasya");
            case "tokat" -> new KaradenizDessert("Tokat Zile Pekmezi", "Zile pekmezli tatlı", 50, "Tokat");
            case "duzce" -> new KaradenizDessert("Düzce Fındıklı Tatlı", "Fındıklı sütlü tatlı", 55, "Düzce");
            case "zonguldak" -> new KaradenizDessert("Zonguldak Gömeçi", "Zonguldak geleneksel gömeç", 45, "Zonguldak");
            case "bartin" -> new KaradenizDessert("Bartın Şeker Helvası", "Bartın geleneksel helvası", 45, "Bartın");
            case "kastamonu" -> new KaradenizDessert("Kastamonu Pastası", "Kastamonu geleneksel pastası", 60, "Kastamonu");
            case "corum" -> new KaradenizDessert("Çorum Leblebisi", "Çorum'un meşhur leblebisi", 35, "Çorum");
            case "bayburt" -> new KaradenizDessert("Bayburt Helvası", "Bayburt un helvası", 42, "Bayburt");
            case "gumushane" -> new KaradenizDessert("Pestil", "Gümüşhane pestili", 40, "Gümüşhane");
            case "karabuk" -> new KaradenizDessert("Karabük Helvası", "Karabük geleneksel helvası", 45, "Karabük");
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
            case "ordu" -> new KaradenizBeverage("Fındık Sütü", "Ordu fındık sütü", 30, "Ordu");
            case "artvin" -> new KaradenizBeverage("Dağ Çayı", "Artvin dağ çayı", 15, "Artvin");
            case "sinop" -> new KaradenizBeverage("Ayva Çayı", "Sinop ayva çayı", 20, "Sinop");
            case "bolu" -> new KaradenizBeverage("Mengen Çayı", "Bolu dağ çayı", 18, "Bolu");
            case "amasya" -> new KaradenizBeverage("Amasya Şırası", "Amasya üzüm şırası", 22, "Amasya");
            case "tokat" -> new KaradenizBeverage("Tokat Şırası", "Tokat üzüm şırası", 20, "Tokat");
            case "duzce" -> new KaradenizBeverage("Düzce Çayı", "Düzce demleme çayı", 15, "Düzce");
            case "zonguldak" -> new KaradenizBeverage("Zonguldak Çayı", "Zonguldak demleme çayı", 15, "Zonguldak");
            case "bartin" -> new KaradenizBeverage("Bartın Çayı", "Bartın demleme çayı", 14, "Bartın");
            case "kastamonu" -> new KaradenizBeverage("Kastamonu Çayı", "Kastamonu demleme çayı", 15, "Kastamonu");
            case "corum" -> new KaradenizBeverage("Çorum Ayranı", "Çorum yayık ayranı", 18, "Çorum");
            case "bayburt" -> new KaradenizBeverage("Bayburt Çayı", "Bayburt yayla çayı", 14, "Bayburt");
            case "gumushane" -> new KaradenizBeverage("Gümüşhane Çayı", "Gümüşhane demleme çayı", 15, "Gümüşhane");
            case "karabuk" -> new KaradenizBeverage("Karabük Çayı", "Karabük demleme çayı", 15, "Karabük");
            default -> throw new IllegalArgumentException("Bilinmeyen şehir: " + city);
        };
    }

    @Override
    public String getRegionName() { return "Karadeniz"; }

    @Override
    public String getRegionKey() { return "karadeniz"; }

    @Override
    public List<String> getCities() { return List.of("trabzon", "samsun", "rize", "giresun", "ordu", "artvin", "sinop", "bolu", "amasya", "tokat", "duzce", "zonguldak", "bartin", "kastamonu", "corum", "bayburt", "gumushane", "karabuk"); }
}
