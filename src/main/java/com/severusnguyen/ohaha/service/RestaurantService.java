package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.dto.CategoryDTO;
import com.severusnguyen.ohaha.dto.MenuDTO;
import com.severusnguyen.ohaha.dto.RestaurantDTO;
import com.severusnguyen.ohaha.entity.Food;
import com.severusnguyen.ohaha.entity.MenuRestaurant;
import com.severusnguyen.ohaha.entity.RatingRestaurant;
import com.severusnguyen.ohaha.entity.Restaurant;
import com.severusnguyen.ohaha.repository.RestaurantRepository;
import com.severusnguyen.ohaha.service.imp.FileServiceImp;
import com.severusnguyen.ohaha.service.imp.RestaurantServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RestaurantService implements RestaurantServiceImp {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public boolean insertRestaurant(MultipartFile file, String title, String subtitle, String description, boolean is_freeship, String address, String open_date) {
        boolean isInsertSuccess = false;

        try {
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);

            if (isSaveFileSuccess) {
                Restaurant restaurant = new Restaurant();

                restaurant.setTitle(title);
                restaurant.setSubtitle(subtitle);
                restaurant.setDescription(description);
                restaurant.setImage(file.getOriginalFilename());   // 64-13''
                restaurant.setFreeship(is_freeship);
                restaurant.setAddress(address);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
                Date openDate = simpleDateFormat.parse(open_date);
                restaurant.setOpenDate(openDate);

                restaurantRepository.save(restaurant);
                isInsertSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error insert restaurant: " + e.getMessage());
        }

        return isInsertSuccess;
    }

    @Override
    public List<RestaurantDTO> getHomePageRestaurant() {

        List<RestaurantDTO> restaurantDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 6); //trang x, tổng dữ liệu là x
        restaurantRepository.findAll(pageRequest);
        Page<Restaurant> listData = restaurantRepository.findAll(pageRequest);

        //duyệt qua list và gán vào DTO
        for (Restaurant data : listData) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();

            restaurantDTO.setId(data.getId());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubtitle(data.getSubtitle());
            restaurantDTO.setFreeShip(data.getFreeship());
            restaurantDTO.setRating(calculatorRating(data.getListRatingRestaurant()));

            restaurantDTOS.add(restaurantDTO);
        }

        return restaurantDTOS;
    }

    private double calculatorRating(Set<RatingRestaurant> listRating){
        double totalPoint = 0;

        for (RatingRestaurant data : listRating) {
            totalPoint += data.getRatePoint();
        }

        return totalPoint/listRating.size();
    }

    @Override
    public RestaurantDTO getDetailRestaurant(int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        if (restaurant.isPresent()){
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            Restaurant data = restaurant.get();

            restaurantDTO.setTitle(data.getTitle());
            restaurantDTO.setSubtitle(data.getSubtitle());
            restaurantDTO.setDesc(data.getDescription());
            restaurantDTO.setImage(data.getImage());
            restaurantDTO.setRating(calculatorRating(data.getListRatingRestaurant()));
            restaurantDTO.setFreeShip(data.getFreeship());
            restaurantDTO.setOpenDate(data.getOpenDate());

            //Category
            for (MenuRestaurant menuRestaurant : data.getListMenuRestaurant()) { //72-13
                List<MenuDTO> menuDTOList = new ArrayList<>();
                CategoryDTO categoryDTO = new CategoryDTO();

                categoryDTO.setName(menuRestaurant.getCategory().getNameCate());

                //Menu
                for (Food food : menuRestaurant.getCategory().getListFood()) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setId(food.getId());
                    menuDTO.setImage(food.getImage());
                    menuDTO.setFreeship(food.isFreeShip());
                    menuDTO.setTitle(food.getTitle());
                    menuDTO.setDesc(food.getDesc());
                    menuDTO.setPrice(food.getPrice());

                    menuDTOList.add(menuDTO);
                }

                categoryDTO.setMenus(menuDTOList);
                categoryDTOList.add(categoryDTO);
            }
            restaurantDTO.setCategories(categoryDTOList);
        }

        return restaurantDTO;
    }
}
