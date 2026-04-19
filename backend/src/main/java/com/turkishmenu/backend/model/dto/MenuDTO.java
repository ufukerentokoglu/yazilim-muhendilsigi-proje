package com.turkishmenu.backend.model.dto;

import java.util.List;

public class MenuDTO {
    private String region;
    private String city;
    private List<DishDTO> dishes;

    public MenuDTO() {}

    public MenuDTO(String region, String city, List<DishDTO> dishes) {
        this.region = region;
        this.city = city;
        this.dishes = dishes;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public List<DishDTO> getDishes() { return dishes; }
    public void setDishes(List<DishDTO> dishes) { this.dishes = dishes; }
}
