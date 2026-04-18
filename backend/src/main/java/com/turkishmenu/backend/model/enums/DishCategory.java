package com.turkishmenu.backend.model.enums;

public enum DishCategory {
    ANA_YEMEK("Ana Yemek"),
    BASLANGIC("Başlangıç"),
    TATLI("Tatlı"),
    ICECEK("İçecek");

    private final String displayName;

    DishCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
