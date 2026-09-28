package com.messmate.service;

import com.messmate.entity.MenuItemEntity;
import com.messmate.repository.MenuItemRepository;
import com.messmate.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MealRepository mealRepository;

    public MenuItemService(
            MenuItemRepository menuItemRepository,
            MealRepository mealRepository) {

        this.menuItemRepository = menuItemRepository;
        this.mealRepository = mealRepository;
    }

    // ==========================================
    // GET ALL MENU ITEMS
    // ==========================================

    public List<MenuItemEntity> getAllMenuItems() {

        return menuItemRepository.findAll();
    }

    // ==========================================
    // GET MENU ITEM BY ID
    // ==========================================

    public MenuItemEntity getMenuItemById(Long id) {

        return menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Menu item not found with ID: " + id
                        )
                );
    }

    // ==========================================
    // CREATE MENU ITEM
    // ==========================================

    public MenuItemEntity createMenuItem(
            MenuItemEntity menuItem) {

        return menuItemRepository.save(menuItem);
    }

    // ==========================================
    // UPDATE MENU ITEM
    // ==========================================

    public MenuItemEntity updateMenuItem(
            Long id,
            MenuItemEntity menuItem) {

        MenuItemEntity existing =
                menuItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu item not found with ID: " + id
                                )
                        );

        existing.setName(menuItem.getName());
        existing.setCategory(menuItem.getCategory());

        return menuItemRepository.save(existing);
    }

    // ==========================================
    // DELETE MENU ITEM
    // ==========================================

    public void deleteMenuItem(Long id) {

        MenuItemEntity menuItem =
                menuItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu item not found with ID: " + id
                                )
                        );

        // Check whether this menu item is used
        // by any meal.

        boolean isUsed =
                mealRepository.existsByMenuItemId(id);

        if (isUsed) {

            throw new RuntimeException(
                    "Cannot delete this menu item because it is assigned to one or more meals. "
                            + "Delete or update the related meal first."
            );
        }

        menuItemRepository.delete(menuItem);
    }
}