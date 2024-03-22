package com.severusnguyen.ohaha.controller;

import com.severusnguyen.ohaha.payload.ResponseData;
import com.severusnguyen.ohaha.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping()
    public ResponseEntity<?> getHomeRestaurant() {

        ResponseData responseData = new ResponseData(); //Để người dùng biết thành công hay thất bại

        responseData.setData(categoryServiceImp.getCategoryHomePage());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
