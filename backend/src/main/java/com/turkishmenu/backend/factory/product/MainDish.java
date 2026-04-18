package com.turkishmenu.backend.factory.product;

public interface MainDish {
    String getName();
    String getDescription();
    double getPrice();
    String getCity();
    String getRegion();
    int getPrepTime(); // dakika
}
