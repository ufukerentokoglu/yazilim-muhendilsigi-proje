package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.OrderTypeFactory;
import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;
import com.turkishmenu.backend.factory.product.ordertype.Packaging;
import com.turkishmenu.backend.factory.product.ordertype.impl.DineInPackaging;
import com.turkishmenu.backend.factory.product.ordertype.impl.DineInPresentation;
import com.turkishmenu.backend.model.enums.OrderType;
import org.springframework.stereotype.Component;

@Component
public class DineInFactory extends OrderTypeFactory {

    @Override
    public Packaging createPackaging() {
        return new DineInPackaging();
    }

    @Override
    public OrderPresentation createPresentation() {
        return new DineInPresentation();
    }

    @Override
    public OrderType getType() {
        return OrderType.DINE_IN;
    }
}
