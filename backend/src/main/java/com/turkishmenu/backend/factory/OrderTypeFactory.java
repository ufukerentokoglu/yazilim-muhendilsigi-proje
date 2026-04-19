package com.turkishmenu.backend.factory;

import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;
import com.turkishmenu.backend.factory.product.ordertype.Packaging;
import com.turkishmenu.backend.model.enums.OrderType;

/**
 * Abstract Factory - Sipariş Tipi Fabrikası
 * Paket (Takeaway) ve Servis (Dine-In) için kendi Packaging ve OrderPresentation ürünlerini üretir.
 */
public abstract class OrderTypeFactory {
    public abstract Packaging createPackaging();
    public abstract OrderPresentation createPresentation();
    public abstract OrderType getType();
}
