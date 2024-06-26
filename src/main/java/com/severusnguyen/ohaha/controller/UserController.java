package com.severusnguyen.ohaha.controller;

import com.severusnguyen.ohaha.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){

        return  new ResponseEntity<>(userServiceImp.getAllUser(), HttpStatus.OK);
    }
}
