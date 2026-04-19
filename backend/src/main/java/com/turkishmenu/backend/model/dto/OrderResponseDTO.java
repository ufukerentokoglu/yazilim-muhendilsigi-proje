package com.turkishmenu.backend.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private List<OrderLineDTO> lines;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private int estimatedPrepTime;
    private LocalDateTime estimatedReadyAt;
    private Integer tableNumber;
    private String orderNote;
    private LocalDateTime deliveredAt;
    private String orderType;
    private String orderTypeLabel;
    private String orderTypeIcon;
    private String orderTypeColor;
    private String packagingDescription;

    public OrderResponseDTO() {}

    public OrderResponseDTO(Long orderId, String customerName, List<OrderLineDTO> lines, double totalAmount, String status, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.lines = lines;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public List<OrderLineDTO> getLines() { return lines; }
    public void setLines(List<OrderLineDTO> lines) { this.lines = lines; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public int getEstimatedPrepTime() { return estimatedPrepTime; }
    public void setEstimatedPrepTime(int estimatedPrepTime) { this.estimatedPrepTime = estimatedPrepTime; }
    public LocalDateTime getEstimatedReadyAt() { return estimatedReadyAt; }
    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) { this.estimatedReadyAt = estimatedReadyAt; }
    public Integer getTableNumber() { return tableNumber; }
    public void setTableNumber(Integer tableNumber) { this.tableNumber = tableNumber; }
    public String getOrderNote() { return orderNote; }
    public void setOrderNote(String orderNote) { this.orderNote = orderNote; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public String getOrderTypeLabel() { return orderTypeLabel; }
    public void setOrderTypeLabel(String orderTypeLabel) { this.orderTypeLabel = orderTypeLabel; }
    public String getOrderTypeIcon() { return orderTypeIcon; }
    public void setOrderTypeIcon(String orderTypeIcon) { this.orderTypeIcon = orderTypeIcon; }
    public String getOrderTypeColor() { return orderTypeColor; }
    public void setOrderTypeColor(String orderTypeColor) { this.orderTypeColor = orderTypeColor; }
    public String getPackagingDescription() { return packagingDescription; }
    public void setPackagingDescription(String packagingDescription) { this.packagingDescription = packagingDescription; }
}
