package com.turkishmenu.backend.model.enums;

public enum RegionType {
    MARMARA("Marmara"),
    EGE("Ege"),
    AKDENIZ("Akdeniz"),
    IC_ANADOLU("İç Anadolu"),
    KARADENIZ("Karadeniz"),
    DOGU_ANADOLU("Doğu Anadolu"),
    GUNEYDOGU("Güneydoğu Anadolu");

    private final String displayName;

    RegionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}