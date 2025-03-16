package com.example.restaurant_simple_api.service;

import com.example.restaurant_simple_api.model.MenuItem;
import com.example.restaurant_simple_api.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllActiveMenuItems(String searchString) {
        // If the searchString is not null or empty, use a query with a search condition
        if (searchString != null && !searchString.trim().isEmpty()) {
            // Use a custom query method in the repository to find items by status and name
            return menuItemRepository.findByStatusAndNameContainingIgnoreCase(1, searchString);
        } else {
            // If no search string is provided, return all active menu items
            return menuItemRepository.findByStatus(1);
        }
    }


    public List<MenuItem> getMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryCategoryIdAndStatus(categoryId,1);
    }

    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantRestaurantIdAndStatus(restaurantId,1);
    }

    public List<MenuItem> getMenuItemsByRestaurantAndCategory(Long restaurantId,Long categoryId) {
        return menuItemRepository.findByRestaurantRestaurantIdAndStatusAndCategoryCategoryId(restaurantId,1,categoryId);
    }

    public List<MenuItem> getMenuItemsWithSortingAndFiltering(Integer sort, Integer filter, String searchedName) {
        // Fetch all active menu items
        List<MenuItem> activeMenuItems = menuItemRepository.findByStatus(1);

        // Apply searching if searchedName is not null or empty
        if (searchedName != null && !searchedName.trim().isEmpty()) {
            activeMenuItems = activeMenuItems.stream()
                    .filter(menuItem -> menuItem.getName() != null &&
                            menuItem.getName().toLowerCase().contains(searchedName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Apply filtering if filter is not null
        if (filter != null) {
            if (filter == 1) {
                activeMenuItems = activeMenuItems.stream()
                        .filter(menuItem -> menuItem.getIsVeg() == 1)
                        .collect(Collectors.toList());
            } else if (filter == 0) {
                activeMenuItems = activeMenuItems.stream()
                        .filter(menuItem -> menuItem.getIsVeg() == 0)
                        .collect(Collectors.toList());
            }
        }

        // Apply sorting if sort is not null
        if (sort != null) {
            if (sort == 1) {
                activeMenuItems = activeMenuItems.stream()
                        .sorted(Comparator.comparing(MenuItem::getPrice))
                        .collect(Collectors.toList());
            } else if (sort == 2) {
                activeMenuItems = activeMenuItems.stream()
                        .sorted(Comparator.comparing(MenuItem::getName, String.CASE_INSENSITIVE_ORDER))
                        .collect(Collectors.toList());
            }
        }

        return activeMenuItems;
    }

}
