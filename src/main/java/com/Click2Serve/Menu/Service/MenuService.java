package com.Click2Serve.Menu.Service;

import com.Click2Serve.Category.DTO.CategoryMenuDTO;
import com.Click2Serve.Category.Entity.Category;
import com.Click2Serve.Exception.ResponseClass;
import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Menu.Entity.MenuItem;
import com.Click2Serve.Hotel.DTO.HotelSummaryDTO;
import com.Click2Serve.Category.Repository.CategoryRepository;
import com.Click2Serve.Hotel.Repository.HotelRepository;
import com.Click2Serve.Menu.DTO.MenuResponseDTO;
import com.Click2Serve.Menu.DTO.MenueItemDTO;
import com.Click2Serve.Menu.Repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private   HotelRepository hotelRepository;

    public MenuService(MenuItemRepository menuItemRepository,
                       CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryMenuDTO> getHotelMenuNested(Long hotelId)
    {

        List<MenuItem> allItems = menuItemRepository.findByHotel_IdAndActiveTrue(hotelId);


        Map<Category, List<MenuItem>> groupedMap = allItems.stream()
                .collect(Collectors.groupingBy(item ->
                        item.getCategory() != null ? item.getCategory() : new Category() // Handle null category
                ));


        List<CategoryMenuDTO> responseList = groupedMap.entrySet().stream().map(entry -> {
            CategoryMenuDTO categoryDTO = new CategoryMenuDTO();


            Category cat = entry.getKey();
            categoryDTO.setCategoryId(cat.getId());
            categoryDTO.setCategoryName(cat.getId() == null ? "General" : cat.getName());


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

        return (List<CategoryMenuDTO>) ResponseClass.responseSuccess("","", responseList);

    }




    public List<MenuItem> getMenuItemsForCustomerByHotel(Long hotelId, Category category) {
        if (category != null) {

            return menuItemRepository.findByHotel_IdAndCategoryAndActiveTrue(hotelId, category);
        }
        else {

            return menuItemRepository.findByHotel_IdAndActiveTrue(hotelId);
        }


    }


    public List<MenuItem> getMenuItemsForAdminByHotel(Long hotelId, Category category) {
        if (category != null) {

            return menuItemRepository.findByHotel_IdAndCategory(hotelId, category);
        } else {

            return menuItemRepository.findByHotel_Id(hotelId);
        }
    }

    public ResponseEntity<?> addMenuItem(MenuItem menuItem) {


        if (menuItem.getHotel() == null || menuItem.getHotel().getId() == null) {
            throw new RuntimeException("Hotel ID is required");
        }

        Hotel hotel = hotelRepository.findById(menuItem.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

               menuItem.setHotel(hotel);



        if (menuItem.getCategory() != null && menuItem.getCategory().getId() != null) {

            Category category = categoryRepository.findById(menuItem.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            menuItem.setCategory(category);
        }



        menuItem.setActive(true);


        MenuItem saved = menuItemRepository.save(menuItem);

        MenuResponseDTO  dto= buildMenuResponse(saved);
         return ResponseClass.responseSuccess("menu has been created ","menuItem",dto);


    }


    private MenuResponseDTO buildMenuResponse(MenuItem item) {

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


    public void disableMenuItem(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(false);
        menuItemRepository.save(item);
    }


    public void enableMenuItem(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        item.setActive(true);
        menuItemRepository.save(item);
    }


    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
