package com.turkishmenu.backend.controller;

import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.entity.DishEntity;
import com.turkishmenu.backend.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "*")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{regionKey}")
    public List<DishDTO> listByRegion(@PathVariable String regionKey) {
        return dishService.findByRegion(regionKey);
    }

    @PostMapping("/{regionKey}")
    public DishDTO create(@PathVariable String regionKey, @RequestBody DishDTO dto) {
        DishEntity created = dishService.create(dto, regionKey);
        return dishService.toDTO(created);
    }

    @PutMapping("/{id}")
    public DishDTO update(@PathVariable Long id, @RequestBody DishDTO dto) {
        DishEntity updated = dishService.update(id, dto);
        return dishService.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        dishService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Yemek silindi"));
    }
}
