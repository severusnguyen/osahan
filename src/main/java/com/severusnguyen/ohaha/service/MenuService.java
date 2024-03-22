package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.entity.Category;
import com.severusnguyen.ohaha.entity.Food;
import com.severusnguyen.ohaha.repository.FoodRepository;
import com.severusnguyen.ohaha.service.imp.FileServiceImp;
import com.severusnguyen.ohaha.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MenuService implements MenuServiceImp {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public boolean createMenu(MultipartFile file, String title, String time_ship, String is_freeship, String price, int cate_id) {
        boolean isInsertSuccess = false;

        try {
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);

            if (isSaveFileSuccess) {
                Food food = new Food();

                food.setTitle(title);
                food.setImage(file.getOriginalFilename());
                food.setTimeShip(time_ship);
                food.setPrice(price); //68-18

                Category category = new Category();
                category.setId(cate_id);

                food.setCategory(category);

                foodRepository.save(food);

                isInsertSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error insert restaurant: " + e.getMessage());
        }

        return isInsertSuccess;
    }
}
