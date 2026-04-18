package com.turkishmenu.backend.model.dto;

import java.util.List;

public class OrderRequestDTO {
    private String customerName;
    private List<OrderItemDTO> items;

    public OrderRequestDTO() {}

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
