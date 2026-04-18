package com.turkishmenu.backend.controller;

import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.entity.PriceOverrideEntity;
import com.turkishmenu.backend.service.MenuService;
import com.turkishmenu.backend.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "*")
public class PriceController {

    private final PriceService priceService;
    private final MenuService menuService;

    public PriceController(PriceService priceService, MenuService menuService) {
        this.priceService = priceService;
        this.menuService = menuService;
    }

    @GetMapping("/{regionKey}")
    public List<DishDTO> getDishesWithPrices(@PathVariable String regionKey) {
        return menuService.getAllDishes(regionKey);
    }

    @GetMapping("/overrides")
    public List<PriceOverrideEntity> getOverrides() {
        return priceService.getAllOverrides();
    }

    @PatchMapping
    public PriceOverrideEntity updatePrice(@RequestBody Map<String, Object> body) {
        String regionKey = (String) body.get("regionKey");
        String city = (String) body.get("city");
        String category = (String) body.get("category");
        double newPrice = ((Number) body.get("newPrice")).doubleValue();
        return priceService.setPrice(regionKey, city, category, newPrice);
    }

    @DeleteMapping
    public Map<String, String> resetPrice(@RequestBody Map<String, String> body) {
        priceService.resetPrice(body.get("regionKey"), body.get("city"), body.get("category"));
        return Map.of("message", "Fiyat varsayılana döndürüldü");
    }
}
