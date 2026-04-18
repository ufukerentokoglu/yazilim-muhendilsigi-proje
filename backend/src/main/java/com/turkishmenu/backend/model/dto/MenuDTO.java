package com.turkishmenu.backend.model.dto;

import java.util.List;

public class MenuDTO {
    private String region;
    private String city;
    private DishDTO mainDish;
    private DishDTO appetizer;
    private DishDTO dessert;
    private DishDTO beverage;

    public MenuDTO() {}

    public MenuDTO(String region, String city, DishDTO mainDish, DishDTO appetizer, DishDTO dessert, DishDTO beverage) {
        this.region = region;
        this.city = city;
        this.mainDish = mainDish;
        this.appetizer = appetizer;
        this.dessert = dessert;
        this.beverage = beverage;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public DishDTO getMainDish() { return mainDish; }
    public void setMainDish(DishDTO mainDish) { this.mainDish = mainDish; }
    public DishDTO getAppetizer() { return appetizer; }
    public void setAppetizer(DishDTO appetizer) { this.appetizer = appetizer; }
    public DishDTO getDessert() { return dessert; }
    public void setDessert(DishDTO dessert) { this.dessert = dessert; }
    public DishDTO getBeverage() { return beverage; }
    public void setBeverage(DishDTO beverage) { this.beverage = beverage; }
}
