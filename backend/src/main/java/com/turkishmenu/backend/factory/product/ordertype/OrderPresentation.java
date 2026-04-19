package com.turkishmenu.backend.factory.product.ordertype;

/**
 * Abstract Factory ürünü - sipariş tipi gösterim bilgileri (admin & müşteri).
 */
public interface OrderPresentation {
    String getLabel();
    String getIcon();
    String getBadgeColor();
    boolean requiresTable();
}
