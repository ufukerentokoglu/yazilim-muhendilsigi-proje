package com.turkishmenu.backend.controller;

import com.turkishmenu.backend.model.dto.DishDTO;
import com.turkishmenu.backend.model.dto.MenuDTO;
import com.turkishmenu.backend.model.dto.RegionDTO;
import com.turkishmenu.backend.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/regions")
    public List<RegionDTO> getRegions() {
        return menuService.getAllRegions();
    }

    @GetMapping("/menu/{regionKey}/{city}")
    public MenuDTO getMenu(@PathVariable String regionKey, @PathVariable String city) {
        return menuService.getMenu(regionKey, city);
    }
}
