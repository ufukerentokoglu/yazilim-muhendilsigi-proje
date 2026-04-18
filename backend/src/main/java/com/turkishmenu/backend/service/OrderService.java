package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.FactoryProvider;
import com.turkishmenu.backend.factory.RegionFactory;
import com.turkishmenu.backend.factory.product.*;
import com.turkishmenu.backend.model.dto.*;
import com.turkishmenu.backend.model.entity.OrderEntity;
import com.turkishmenu.backend.model.entity.OrderLineEntity;
import com.turkishmenu.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final FactoryProvider factoryProvider;
    private final OrderRepository orderRepository;

    public OrderService(FactoryProvider factoryProvider, OrderRepository orderRepository) {
        this.factoryProvider = factoryProvider;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        OrderEntity order = new OrderEntity();
        order.setCustomerName(request.getCustomerName());
        order.setStatus("Bekliyor");

        double totalAmount = 0;
        int maxPrepTime = 0;

        for (OrderItemDTO item : request.getItems()) {
            RegionFactory factory = factoryProvider.getFactory(item.getRegionKey());
            String city = item.getCity();
            String category = item.getCategory();
            int quantity = item.getQuantity();

            String dishName;
            double price;
            int prepTime;

            switch (category) {
                case "Ana Yemek" -> {
                    MainDish dish = factory.createMainDish(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                    prepTime = dish.getPrepTime();
                }
                case "Başlangıç" -> {
                    Appetizer dish = factory.createAppetizer(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                    prepTime = dish.getPrepTime();
                }
                case "Tatlı" -> {
                    Dessert dish = factory.createDessert(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                    prepTime = dish.getPrepTime();
                }
                case "İçecek" -> {
                    Beverage dish = factory.createBeverage(city);
                    dishName = dish.getName();
                    price = dish.getPrice();
                    prepTime = dish.getPrepTime();
                }
                default -> throw new IllegalArgumentException("Bilinmeyen kategori: " + category);
            }

            OrderLineEntity line = new OrderLineEntity(
                    dishName, category, city,
                    factory.getRegionName(), price, quantity, prepTime
            );
            order.addLine(line);
            totalAmount += line.getLineTotal();
            maxPrepTime = Math.max(maxPrepTime, prepTime);
        }

        order.setTotalAmount(totalAmount);
        order.setEstimatedPrepTime(maxPrepTime);
        order.setEstimatedReadyAt(LocalDateTime.now().plusMinutes(maxPrepTime));

        OrderEntity saved = orderRepository.save(order);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı: " + orderId));
        return toDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getActiveOrders() {
        return orderRepository.findByArchivedFalseOrderByCreatedAtDesc()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long orderId, String newStatus) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı: " + orderId));
        order.setStatus(newStatus);
        return toDTO(orderRepository.save(order));
    }

    @Transactional
    public void archiveOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı: " + orderId));
        order.setArchived(true);
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public String generateDayEndReport() {
        List<OrderEntity> all = orderRepository.findAllByOrderByCreatedAtDesc();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("==============================================\n");
        sb.append("       GÜN SONU RAPORU\n");
        sb.append("       Tarih: ").append(LocalDateTime.now().format(dateFmt)).append("\n");
        sb.append("==============================================\n\n");

        long delivered = all.stream().filter(o -> o.getStatus().equals("Teslim Edildi")).count();
        long cancelled = all.stream().filter(o -> o.getStatus().equals("İptal Edildi")).count();
        double totalRevenue = all.stream()
                .filter(o -> o.getStatus().equals("Teslim Edildi"))
                .mapToDouble(OrderEntity::getTotalAmount).sum();

        sb.append("ÖZET\n");
        sb.append("----------------------------------------------\n");
        sb.append(String.format("Toplam Sipariş     : %d\n", all.size()));
        sb.append(String.format("Teslim Edilen      : %d\n", delivered));
        sb.append(String.format("İptal Edilen       : %d\n", cancelled));
        sb.append(String.format("Toplam Ciro        : ₺%.2f\n", totalRevenue));
        sb.append("\n");

        sb.append("SİPARİŞ DETAYLARI\n");
        sb.append("==============================================\n");

        for (OrderEntity order : all) {
            sb.append(String.format("\nSipariş #%d | %s | %s | %s\n",
                    order.getId(),
                    order.getCustomerName(),
                    order.getStatus(),
                    order.getCreatedAt() != null ? order.getCreatedAt().format(timeFmt) : "-"));
            sb.append("----------------------------------------------\n");

            for (OrderLineEntity line : order.getLines()) {
                sb.append(String.format("  %-25s %s / %-12s x%d  ₺%.0f\n",
                        line.getDishName(),
                        line.getRegion(),
                        line.getCity(),
                        line.getQuantity(),
                        line.getLineTotal()));
            }
            sb.append(String.format("  TOPLAM: ₺%.2f\n", order.getTotalAmount()));
        }

        sb.append("\n==============================================\n");
        sb.append("Rapor otomatik oluşturulmuştur.\n");

        return sb.toString();
    }

    @Transactional
    public void resetAllOrders() {
        orderRepository.deleteAll();
    }

    private OrderResponseDTO toDTO(OrderEntity entity) {
        List<OrderLineDTO> lines = entity.getLines().stream()
                .map(l -> {
                    OrderLineDTO dto = new OrderLineDTO(
                            l.getDishName(), l.getCategory(), l.getCity(),
                            l.getRegion(), l.getUnitPrice(), l.getQuantity()
                    );
                    dto.setPrepTime(l.getPrepTime());
                    return dto;
                })
                .collect(Collectors.toList());

        OrderResponseDTO dto = new OrderResponseDTO(
                entity.getId(), entity.getCustomerName(), lines,
                entity.getTotalAmount(), entity.getStatus(), entity.getCreatedAt()
        );
        dto.setEstimatedPrepTime(entity.getEstimatedPrepTime());
        dto.setEstimatedReadyAt(entity.getEstimatedReadyAt());
        return dto;
    }
}
