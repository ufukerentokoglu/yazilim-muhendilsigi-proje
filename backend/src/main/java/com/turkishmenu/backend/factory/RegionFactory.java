package com.turkishmenu.backend.factory;

import com.turkishmenu.backend.factory.product.Appetizer;
import com.turkishmenu.backend.factory.product.Beverage;
import com.turkishmenu.backend.factory.product.Dessert;
import com.turkishmenu.backend.factory.product.MainDish;

import java.util.List;

/**
 * Abstract Factory - Bölgesel Mutfak Fabrikası
 * Her bölge bu sınıfı extend ederek kendi yöresel yemeklerini üretir.
 */
public abstract class RegionFactory {

    public abstract MainDish createMainDish(String city);

    public abstract Appetizer createAppetizer(String city);

    public abstract Dessert createDessert(String city);

    public abstract Beverage createBeverage(String city);

    public abstract String getRegionName();

    public abstract String getRegionKey();

    public abstract List<String> getCities();
}
