package com.turkishmenu.backend.factory.product.ordertype.impl;

import com.turkishmenu.backend.factory.product.ordertype.Packaging;

public class TakeawayPackaging implements Packaging {
    @Override
    public String getDescription() {
        return "Paket kutusu ile hijyenik ambalaj";
    }

    @Override
    public int getExtraPrepMinutes() {
        return 5;
    }

    @Override
    public String getIcon() {
        return "📦";
    }
}
