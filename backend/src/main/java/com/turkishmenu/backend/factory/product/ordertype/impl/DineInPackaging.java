package com.turkishmenu.backend.factory.product.ordertype.impl;

import com.turkishmenu.backend.factory.product.ordertype.Packaging;

public class DineInPackaging implements Packaging {
    @Override
    public String getDescription() {
        return "Masaya sıcak servis, porselen tabakta";
    }

    @Override
    public int getExtraPrepMinutes() {
        return 0;
    }

    @Override
    public String getIcon() {
        return "🍽️";
    }
}
