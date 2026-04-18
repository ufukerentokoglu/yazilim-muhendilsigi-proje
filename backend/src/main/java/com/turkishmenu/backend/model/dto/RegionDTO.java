package com.turkishmenu.backend.model.dto;

import java.util.List;

public class RegionDTO {
    private String key;
    private String name;
    private List<String> cities;

    public RegionDTO() {}

    public RegionDTO(String key, String name, List<String> cities) {
        this.key = key;
        this.name = name;
        this.cities = cities;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getCities() { return cities; }
    public void setCities(List<String> cities) { this.cities = cities; }
}
