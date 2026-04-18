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
import com.turkishmenu.backend.model.dto.OrderLineDTO;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/popular")
    public List<Map<String, Object>> getPopularDishes() {
        List<OrderResponseDTO> all = orderService.getAllOrders();
        Map<String, int[]> dishCount = new LinkedHashMap<>();

        for (OrderResponseDTO order : all) {
            if (order.getStatus().equals("İptal Edildi")) continue;
            for (OrderLineDTO line : order.getLines()) {
                dishCount.computeIfAbsent(line.getDishName(), k -> new int[]{0, 0});
                dishCount.get(line.getDishName())[0] += line.getQuantity();
                if (!dishCount.containsKey(line.getDishName() + "_meta")) {
                    dishCount.put(line.getDishName() + "_meta", new int[]{0, 0});
                }
            }
        }

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        for (OrderResponseDTO order : all) {
            if (order.getStatus().equals("İptal Edildi")) continue;
            for (OrderLineDTO line : order.getLines()) {
                result.computeIfAbsent(line.getDishName(), k -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("name", line.getDishName());
                    m.put("category", line.getCategory());
                    m.put("region", line.getRegion());
                    m.put("city", line.getCity());
                    m.put("count", 0);
                    return m;
                });
                result.get(line.getDishName()).merge("count", line.getQuantity(), (a, b) -> (int) a + (int) b);
            }
        }

        return result.values().stream()
                .sorted((a, b) -> Integer.compare((int) b.get("count"), (int) a.get("count")))
                .limit(10)
                .collect(Collectors.toList());
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
