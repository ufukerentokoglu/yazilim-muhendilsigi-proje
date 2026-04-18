package com.turkishmenu.backend.factory.product.impl.karadeniz;

import com.turkishmenu.backend.factory.product.Dessert;

public class KaradenizDessert implements Dessert {
    private final String name;
    private final String description;
    private final double price;
    private final String city;

    public KaradenizDessert(String name, String description, double price, String city) {
        this.name = name; this.description = description; this.price = price; this.city = city;
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public double getPrice() { return price; }
    @Override public String getCity() { return city; }
    @Override public String getRegion() { return "Karadeniz"; }
}
