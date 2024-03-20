package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.dto.RestaurantDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
}
