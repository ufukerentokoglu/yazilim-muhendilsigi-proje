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
}
