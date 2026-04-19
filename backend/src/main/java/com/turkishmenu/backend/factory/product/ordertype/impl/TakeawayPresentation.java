package com.turkishmenu.backend.factory.product.ordertype.impl;

import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;

public class TakeawayPresentation implements OrderPresentation {
    @Override
    public String getLabel() {
        return "Paket";
    }

    @Override
    public String getIcon() {
        return "📦";
    }

    @Override
    public String getBadgeColor() {
        return "#e67e22";
    }

    @Override
    public boolean requiresTable() {
        return false;
    }
}
