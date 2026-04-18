package com.turkishmenu.backend.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "price_overrides")
public class PriceOverrideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regionKey;
    private String city;
    private String category;
    private double newPrice;

    public PriceOverrideEntity() {}

    public PriceOverrideEntity(String regionKey, String city, String category, double newPrice) {
        this.regionKey = regionKey;
        this.city = city;
        this.category = category;
        this.newPrice = newPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRegionKey() { return regionKey; }
    public void setRegionKey(String regionKey) { this.regionKey = regionKey; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getNewPrice() { return newPrice; }
    public void setNewPrice(double newPrice) { this.newPrice = newPrice; }
}
