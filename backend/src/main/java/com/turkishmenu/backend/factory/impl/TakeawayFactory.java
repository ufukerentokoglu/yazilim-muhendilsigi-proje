package com.turkishmenu.backend.factory.impl;

import com.turkishmenu.backend.factory.OrderTypeFactory;
import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;
import com.turkishmenu.backend.factory.product.ordertype.Packaging;
import com.turkishmenu.backend.factory.product.ordertype.impl.TakeawayPackaging;
import com.turkishmenu.backend.factory.product.ordertype.impl.TakeawayPresentation;
import com.turkishmenu.backend.model.enums.OrderType;
import org.springframework.stereotype.Component;

@Component
public class TakeawayFactory extends OrderTypeFactory {

    @Override
    public Packaging createPackaging() {
        return new TakeawayPackaging();
    }

    @Override
    public OrderPresentation createPresentation() {
        return new TakeawayPresentation();
    }

    @Override
    public OrderType getType() {
        return OrderType.TAKEAWAY;
    }
}
