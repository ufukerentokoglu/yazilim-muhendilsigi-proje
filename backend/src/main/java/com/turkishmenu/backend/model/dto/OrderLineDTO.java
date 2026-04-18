package com.turkishmenu.backend.model.dto;

public class OrderLineDTO {
    private String dishName;
    private String category;
    private String city;
    private String region;
    private double unitPrice;
    private int quantity;
    private double lineTotal;

    public OrderLineDTO() {}

    public OrderLineDTO(String dishName, String category, String city, String region, double unitPrice, int quantity) {
        this.dishName = dishName;
        this.category = category;
        this.city = city;
        this.region = region;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = unitPrice * quantity;
    }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getLineTotal() { return lineTotal; }
    public void setLineTotal(double lineTotal) { this.lineTotal = lineTotal; }
}
