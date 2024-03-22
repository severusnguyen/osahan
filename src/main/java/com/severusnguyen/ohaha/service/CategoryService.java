package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.dto.CategoryDTO;
import com.severusnguyen.ohaha.dto.MenuDTO;
import com.severusnguyen.ohaha.entity.Category;
import com.severusnguyen.ohaha.entity.Food;
import com.severusnguyen.ohaha.repository.CategoryRepository;
import com.severusnguyen.ohaha.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getCategoryHomePage() {

        PageRequest pageRequest =  PageRequest.of(0, 3, Sort.by("id"));
        Page<Category> listCategory = categoryRepository.findAll(pageRequest);
        List<CategoryDTO> listCategoryDTOS = new ArrayList<>();

        for (Category data : listCategory){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(data.getNameCate());

            List<MenuDTO> menuDTOS = new ArrayList<>(); //69-17

            for (Food dataFood : data.getListFood()) {
                MenuDTO menuDTO = new MenuDTO();

                menuDTO.setTitle(dataFood.getTitle());
                menuDTO.setFreeship(dataFood.isFreeShip());
                menuDTO.setImage(dataFood.getImage());
                
                menuDTOS.add(menuDTO);
            }
            categoryDTO.setMenus(menuDTOS);

            listCategoryDTOS.add(categoryDTO);
        }

        return listCategoryDTOS;
    }
}
