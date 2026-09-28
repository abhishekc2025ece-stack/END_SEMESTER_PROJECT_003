package com.messmate.controller;

import com.messmate.entity.MenuItemEntity;
import com.messmate.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService service;

    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<MenuItemEntity> getAllMenuItems() {
        return service.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public MenuItemEntity getMenuItemById(
            @PathVariable Long id) {

        return service.getMenuItemById(id);
    }

    @PostMapping
    public MenuItemEntity createMenuItem(
            @Valid @RequestBody MenuItemEntity menuItem) {

        return service.createMenuItem(menuItem);
    }

    @PutMapping("/{id}")
    public MenuItemEntity updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemEntity menuItem) {

        return service.updateMenuItem(id, menuItem);
    }

    @DeleteMapping("/{id}")
    public String deleteMenuItem(
            @PathVariable Long id) {

        service.deleteMenuItem(id);

        return "Menu item deleted successfully";
    }
}