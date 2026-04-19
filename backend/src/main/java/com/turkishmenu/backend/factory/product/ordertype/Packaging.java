package com.turkishmenu.backend.factory.product.ordertype;

/**
 * Abstract Factory ürünü - sipariş paketleme davranışı.
 * Paket (takeaway) ve Servis (dine-in) tipleri kendi Packaging implementasyonlarını döner.
 */
public interface Packaging {
    String getDescription();
    int getExtraPrepMinutes();
    String getIcon();
}
