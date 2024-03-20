package com.severusnguyen.ohaha.service.imp;

import com.severusnguyen.ohaha.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface RestaurantServiceImp {
    boolean insertRestaurant( @RequestParam MultipartFile file,
                              @RequestParam String title,
                              @RequestParam String subtitle,
                              @RequestParam String description,
                              @RequestParam boolean is_freeship,
                              @RequestParam String address,
                              @RequestParam String open_date);

    List<RestaurantDTO> getHomePageRestaurant();

}
