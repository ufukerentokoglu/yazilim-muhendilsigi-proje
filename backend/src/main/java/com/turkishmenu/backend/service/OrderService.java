package com.turkishmenu.backend.service;

import com.turkishmenu.backend.factory.OrderTypeFactory;
import com.turkishmenu.backend.factory.OrderTypeFactoryProvider;
import com.turkishmenu.backend.factory.product.ordertype.OrderPresentation;
import com.turkishmenu.backend.factory.product.ordertype.Packaging;
import com.turkishmenu.backend.model.dto.*;
import com.turkishmenu.backend.model.entity.DishEntity;
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

    private final OrderTypeFactoryProvider orderTypeFactoryProvider;
    private final OrderRepository orderRepository;
    private final DishService dishService;

    public OrderService(OrderTypeFactoryProvider orderTypeFactoryProvider,
                        OrderRepository orderRepository,
                        DishService dishService) {
        this.orderTypeFactoryProvider = orderTypeFactoryProvider;
        this.orderRepository = orderRepository;
        this.dishService = dishService;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        OrderTypeFactory orderTypeFactory = orderTypeFactoryProvider.getFactory(request.getOrderType());
        Packaging packaging = orderTypeFactory.createPackaging();
        OrderPresentation presentation = orderTypeFactory.createPresentation();

        if (presentation.requiresTable() && (request.getTableNumber() == null || request.getTableNumber() <= 0)) {
            throw new IllegalArgumentException("Servis siparişi için masa numarası zorunludur");
        }

        OrderEntity order = new OrderEntity();
        order.setCustomerName(request.getCustomerName());
        order.setTableNumber(presentation.requiresTable() ? request.getTableNumber() : null);
        order.setOrderNote(request.getOrderNote());
        order.setOrderType(orderTypeFactory.getType().name());
        order.setStatus("Bekliyor");

        double totalAmount = 0;
        int maxPrepTime = 0;

        for (OrderItemDTO item : request.getItems()) {
            int quantity = item.getQuantity();

            DishEntity dish;
            if (item.getDishId() != null) {
                dish = dishService.findById(item.getDishId())
                        .orElseThrow(() -> new IllegalArgumentException("Yemek bulunamadı: id=" + item.getDishId()));
            } else {
                dish = dishService.findByRegionCityCategory(item.getRegionKey(), item.getCity(), item.getCategory())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Yemek bulunamadı: " + item.getRegionKey() + "/" + item.getCity() + "/" + item.getCategory()));
            }

            OrderLineEntity line = new OrderLineEntity(
                    dish.getName(), dish.getCategory(), dish.getCity(),
                    dish.getRegionName(), dish.getPrice(), quantity, dish.getPrepTime()
            );
            order.addLine(line);
            totalAmount += line.getLineTotal();
            maxPrepTime = Math.max(maxPrepTime, dish.getPrepTime());
        }

        int totalPrepTime = maxPrepTime + packaging.getExtraPrepMinutes();
        order.setTotalAmount(totalAmount);
        order.setEstimatedPrepTime(totalPrepTime);
        order.setEstimatedReadyAt(LocalDateTime.now().plusMinutes(totalPrepTime));

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
        if ("Teslim Edildi".equals(newStatus)) {
            order.setDeliveredAt(LocalDateTime.now());
        }
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
        dto.setTableNumber(entity.getTableNumber());
        dto.setOrderNote(entity.getOrderNote());
        dto.setDeliveredAt(entity.getDeliveredAt());

        String typeKey = entity.getOrderType() != null ? entity.getOrderType() : "DINE_IN";
        OrderTypeFactory typeFactory = orderTypeFactoryProvider.getFactory(typeKey);
        OrderPresentation presentation = typeFactory.createPresentation();
        Packaging packaging = typeFactory.createPackaging();
        dto.setOrderType(typeFactory.getType().name());
        dto.setOrderTypeLabel(presentation.getLabel());
        dto.setOrderTypeIcon(presentation.getIcon());
        dto.setOrderTypeColor(presentation.getBadgeColor());
        dto.setPackagingDescription(packaging.getDescription());
        return dto;
    }
}
