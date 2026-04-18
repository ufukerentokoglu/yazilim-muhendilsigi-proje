package com.turkishmenu.backend.controller;

import com.turkishmenu.backend.model.dto.OrderRequestDTO;
import com.turkishmenu.backend.model.dto.OrderResponseDTO;
import com.turkishmenu.backend.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping
    public List<OrderResponseDTO> getActiveOrders() {
        return orderService.getActiveOrders();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<OrderResponseDTO> all = orderService.getAllOrders();
        long total = all.stream().filter(o -> !o.getStatus().equals("İptal Edildi")).count();
        long active = all.stream().filter(o ->
                !o.getStatus().equals("Teslim Edildi")
                && !o.getStatus().equals("İptal Edildi")).count();
        long cancelled = all.stream().filter(o -> o.getStatus().equals("İptal Edildi")).count();
        double revenue = all.stream()
                .filter(o -> o.getStatus().equals("Teslim Edildi"))
                .mapToDouble(OrderResponseDTO::getTotalAmount).sum();
        return Map.of("total", total, "active", active, "cancelled", cancelled, "revenue", revenue);
    }

    @PatchMapping("/{orderId}/status")
    public OrderResponseDTO updateStatus(@PathVariable Long orderId, @RequestBody Map<String, String> body) {
        return orderService.updateStatus(orderId, body.get("status"));
    }

    @PatchMapping("/{orderId}/cancel")
    public OrderResponseDTO cancelOrder(@PathVariable Long orderId) {
        return orderService.updateStatus(orderId, "İptal Edildi");
    }

    @DeleteMapping("/{orderId}")
    public Map<String, String> archiveOrder(@PathVariable Long orderId) {
        orderService.archiveOrder(orderId);
        return Map.of("message", "Sipariş arşivlendi", "orderId", orderId.toString());
    }

    @PostMapping("/day-end")
    public ResponseEntity<byte[]> dayEnd() {
        String report = orderService.generateDayEndReport();
        orderService.resetAllOrders();

        String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.TEXT_PLAIN)
                .body(report.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
