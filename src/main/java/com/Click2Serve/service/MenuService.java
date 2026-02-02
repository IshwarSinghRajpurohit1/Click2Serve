package com.Click2Serve.service;

import com.Click2Serve.Dto.*;
import com.Click2Serve.Entity.Category;
import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.MenueItem;
import com.Click2Serve.Repository.CategoryRepository;
import com.Click2Serve.Repository.HotelRepository;
import com.Click2Serve.Repository.MenueItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenueItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private   HotelRepository hotelRepository;

    public MenuService(MenueItemRepository menuItemRepository,
                       CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryMenuDTO> getHotelMenuNested(Long hotelId) {
        // 1. Hotel ke saare ACTIVE items fetch karo
        List<MenueItem> allItems = menuItemRepository.findByHotel_IdAndActiveTrue(hotelId);

        // 2. Items ko Category ke hisaab se group karo
        // Note: Agar kisi item ki category null hai, toh wo "Uncategorized" mein jayega
        Map<Category, List<MenueItem>> groupedMap = allItems.stream()
                .collect(Collectors.groupingBy(item ->
                        item.getCategory() != null ? item.getCategory() : new Category() // Handle null category
                ));

        // 3. Map ko CategoryMenuDTO ki list mein convert karo
        return groupedMap.entrySet().stream().map(entry -> {
            CategoryMenuDTO categoryDTO = new CategoryMenuDTO();

            // Category details set karo
            Category cat = entry.getKey();
            categoryDTO.setCategoryId(cat.getId());
            categoryDTO.setCategoryName(cat.getId() == null ? "General" : cat.getName());

            // Us category ke items ko MenueItemDTO mein convert karo
            List<MenueItemDTO> itemDTOs = entry.getValue().stream().map(item -> {
                MenueItemDTO itemDto = new MenueItemDTO();
                itemDto.setId(item.getId());
                itemDto.setName(item.getName());
                itemDto.setPrice(item.getPrice());
                itemDto.setActive(item.getActive());
                itemDto.setHotelId(hotelId);
                return itemDto;
            }).collect(Collectors.toList());

            categoryDTO.setItems(itemDTOs);
            return categoryDTO;
        }).collect(Collectors.toList());
    }



    // ==========================
    // Customer: active items, category optional
    // ==========================
    public List<MenueItem> getMenuItemsForCustomerByHotel(Long hotelId, Category category) {
        if (category != null) {
            // Category provided → filter by category
            return menuItemRepository.findByHotel_IdAndCategoryAndActiveTrue(hotelId, category);
        } else {
            // Category not provided → return all active items for hotel
            return menuItemRepository.findByHotel_IdAndActiveTrue(hotelId);
        }
    }

    // ==========================
    // Admin: all items, category optional
    // ==========================
    public List<MenueItem> getMenuItemsForAdminByHotel(Long hotelId, Category category) {
        if (category != null) {
            // Category provided → filter by category
            return menuItemRepository.findByHotel_IdAndCategory(hotelId, category);
        } else {
            // Category not provided → return all items for hotel
            return menuItemRepository.findByHotel_Id(hotelId);
        }
    }

    public MenuResponseDTO addMenuItem(MenueItem menuItem) {

        // -----------------------------------------------------------
        // 1️⃣ HOTEL BINDING START (Ye tumne pehle hi kar rakha tha)
        // -----------------------------------------------------------
        if (menuItem.getHotel() == null || menuItem.getHotel().getId() == null) {
            throw new RuntimeException("Hotel ID is required");
        }

        Hotel hotel = hotelRepository.findById(menuItem.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        menuItem.setHotel(hotel); // 👈 YEH HAI HOTEL BINDING
        // -----------------------------------------------------------


        // -----------------------------------------------------------
        // 2️⃣ CATEGORY BINDING START (Ye ab humne naya add kiya hai)
        // -----------------------------------------------------------
        if (menuItem.getCategory() != null && menuItem.getCategory().getId() != null) {

            Category category = categoryRepository.findById(menuItem.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            menuItem.setCategory(category); // 👈 YEH HAI CATEGORY BINDING
        }
        // -----------------------------------------------------------


        menuItem.setActive(true);

        // Yahan Database mein dono Foreign Keys (hotel_id, category_id) save ho jayengi
        MenueItem saved = menuItemRepository.save(menuItem);

        return buildMenuResponse(saved);
    }
    // ==========================
    // Add new menu item
    // ==========================
//    public MenuResponseDTO addMenuItem(MenueItem menuItem) {
//
//        if (menuItem.getHotel() == null || menuItem.getHotel().getId() == null) {
//            throw new RuntimeException("Hotel ID is required");
//        }
//
//        // 🔥 IMPORTANT FIX — DB se hotel lao
//        Hotel hotel = hotelRepository.findById(menuItem.getHotel().getId())
//                .orElseThrow(() -> new RuntimeException("Hotel not found"));
//
//        menuItem.setHotel(hotel);
//        menuItem.setActive(true);
//
//        MenueItem saved = menuItemRepository.save(menuItem);
//
//        // ✅ RESPONSE DTO
//        return buildMenuResponse(saved);
//    }

    private MenuResponseDTO buildMenuResponse(MenueItem item) {

        Hotel hotel = item.getHotel();

        HotelSummaryDTO hotelSummary = HotelSummaryDTO.builder()
                .id(hotel.getId())
                .hotelName(hotel.getHotelName())
                .address(hotel.getAddress())
                .status(hotel.getStatus())
                .build();

        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setActive(item.getActive());
        dto.setHotel(hotelSummary);

        return dto;
    }



//    public MenueItem addMenuItem(MenueItem menuItem) {
//        // Default active true
//        if (menuItem.getActive() == null) {
//            menuItem.setActive(true);
//        }
//
//        // Category optional
//        if (menuItem.getCategory() == null) {
//            menuItem.setCategory(null);
//        }
//
//        // Hotel mandatory
//        if (menuItem.getHotel() == null || menuItem.getHotel().getId() == null) {
//            throw new RuntimeException("Hotel ID is required");
//        }
//
//        return menuItemRepository.save(menuItem);
//    }

    // ==========================
    // Disable menu item
    // ==========================
    public void disableMenuItem(Long id) {
        MenueItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(false);
        menuItemRepository.save(item);
    }

    // ==========================
    // Enable menu item
    // ==========================
    public void enableMenuItem(Long id) {
        MenueItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(true);
        menuItemRepository.save(item);
    }

    // ==========================
    // Get category by ID
    // ==========================
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
