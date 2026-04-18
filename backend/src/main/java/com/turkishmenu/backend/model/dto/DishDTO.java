package com.turkishmenu.backend.model.dto;

public class DishDTO {
    private String name;
    private String description;
    private double price;
    private String city;
    private String region;
    private String category;
    private int prepTime;

    public DishDTO() {}

    public DishDTO(String name, String description, double price, String city, String region, String category, int prepTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.city = city;
        this.region = region;
        this.category = category;
        this.prepTime = prepTime;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
}
