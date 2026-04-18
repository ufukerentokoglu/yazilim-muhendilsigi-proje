package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.model.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final FactoryProvider factoryProvider;
    private final AtomicLong orderIdGenerator = new AtomicLong(1000);
    private final Map<Long, OrderResponseDTO> orders = new ConcurrentHashMap<>();

    public OrderService(FactoryProvider factoryProvider) {
        this.factoryProvider = factoryProvider;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        List<OrderLineDTO> lines = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemDTO item : request.getItems()) {
            RegionFactory factory = factoryProvider.getFactory(item.getRegionKey());
            String city = item.getCity();
            String category = item.getCategory();
            int quantity = item.getQuantity();

            String dishName;
            double price;

            switch (category) {
                case "Ana Yemek" -> {
                    MainDish dish = factory.createMainDish(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                }
                case "Başlangıç" -> {
                    Appetizer dish = factory.createAppetizer(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                }
                case "Tatlı" -> {
                    Dessert dish = factory.createDessert(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                }
                case "İçecek" -> {
                    Beverage dish = factory.createBeverage(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                }
                default -> throw new IllegalArgumentException("Bilinmeyen kategori: " + category);
            }

            OrderLineDTO line = new OrderLineDTO(
                    dishName, category, city,
                    factory.getRegionName(), price, quantity
            );
            lines.add(line);
            totalAmount += line.getLineTotal();
        }

        Long orderId = orderIdGenerator.getAndIncrement();
        OrderResponseDTO response = new OrderResponseDTO(
                orderId, request.getCustomerName(), lines,
                totalAmount, "Onaylandı", LocalDateTime.now()
        );

        orders.put(orderId, response);
        return response;
    }

    public OrderResponseDTO getOrder(Long orderId) {
        OrderResponseDTO order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Sipariş bulunamadı: " + orderId);
        }
        return order;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
