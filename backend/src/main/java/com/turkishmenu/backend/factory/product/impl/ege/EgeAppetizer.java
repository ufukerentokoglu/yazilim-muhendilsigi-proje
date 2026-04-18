package com.turkishmenu.backend.factory.product.impl.ege;

import com.turkishmenu.backend.factory.product.Appetizer;

public class EgeAppetizer implements Appetizer {
    private final String name;
    private final String description;
    private final double price;
    private final String city;

    public EgeAppetizer(String name, String description, double price, String city) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.city = city;
    }

    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public double getPrice() { return price; }
    @Override public String getCity() { return city; }
    @Override public String getRegion() { return "Ege"; }
}
