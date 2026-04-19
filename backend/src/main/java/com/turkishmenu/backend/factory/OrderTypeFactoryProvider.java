package com.turkishmenu.backend.factory;

import com.turkishmenu.backend.model.enums.OrderType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderTypeFactoryProvider {

    private final Map<OrderType, OrderTypeFactory> factories;

    public OrderTypeFactoryProvider(List<OrderTypeFactory> factoryList) {
        this.factories = factoryList.stream()
                .collect(Collectors.toMap(OrderTypeFactory::getType, f -> f));
    }

    public OrderTypeFactory getFactory(OrderType type) {
        OrderTypeFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Bilinmeyen sipariş tipi: " + type);
        }
        return factory;
    }

    public OrderTypeFactory getFactory(String typeKey) {
        if (typeKey == null) return getFactory(OrderType.DINE_IN);
        return switch (typeKey.toUpperCase()) {
            case "TAKEAWAY", "PAKET" -> getFactory(OrderType.TAKEAWAY);
            case "DINE_IN", "SERVIS", "SERVİS" -> getFactory(OrderType.DINE_IN);
            default -> throw new IllegalArgumentException("Bilinmeyen sipariş tipi: " + typeKey);
        };
    }
}
