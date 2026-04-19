package com.turkishmenu.backend.factory.product.ordertype.impl;

import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;

public class DineInPresentation implements OrderPresentation {
    @Override
    public String getLabel() {
        return "Servis";
    }

    @Override
    public String getIcon() {
        return "🍽️";
    }

    @Override
    public String getBadgeColor() {
        return "#2980b9";
    }

    @Override
    public boolean requiresTable() {
        return true;
    }
}
