package com.turkishmenu.backend.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dishes")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String regionKey;

    @Column(nullable = false)
    private String regionName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String category; // "Ana Yemek", "Başlangıç", "Tatlı", "İçecek"

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int prepTime;

    public DishEntity() {}

    public DishEntity(String regionKey, String regionName, String city, String category,
                      String name, String description, double price, int prepTime) {
        this.regionKey = regionKey;
        this.regionName = regionName;
        this.city = city;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.prepTime = prepTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRegionKey() { return regionKey; }
    public void setRegionKey(String regionKey) { this.regionKey = regionKey; }
    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getPrepTime() { return prepTime; }
    public void setPrepTime(int prepTime) { this.prepTime = prepTime; }
}
