package com.turkishmenu.backend.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String status; // BEKLIYOR, HAZIRLANIYOR, HAZIR, TESLIM_EDILDI

    private double totalAmount;

    private int estimatedPrepTime; // dakika

    private LocalDateTime createdAt;

    private LocalDateTime estimatedReadyAt;

    private boolean archived = false;

    private Integer tableNumber;

    private String orderNote;

    private LocalDateTime deliveredAt;

    private String orderType = "DINE_IN";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineEntity> lines = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getEstimatedPrepTime() { return estimatedPrepTime; }
    public void setEstimatedPrepTime(int estimatedPrepTime) { this.estimatedPrepTime = estimatedPrepTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getEstimatedReadyAt() { return estimatedReadyAt; }
    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) { this.estimatedReadyAt = estimatedReadyAt; }

    public List<OrderLineEntity> getLines() { return lines; }
    public void setLines(List<OrderLineEntity> lines) { this.lines = lines; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public Integer getTableNumber() { return tableNumber; }
    public void setTableNumber(Integer tableNumber) { this.tableNumber = tableNumber; }

    public String getOrderNote() { return orderNote; }
    public void setOrderNote(String orderNote) { this.orderNote = orderNote; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public void addLine(OrderLineEntity line) {
        lines.add(line);
        line.setOrder(this);
    }
}
