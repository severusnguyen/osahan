package com.severusnguyen.ohaha.controller;

import com.severusnguyen.ohaha.payload.ResponseData;
import com.severusnguyen.ohaha.payload.request.SignUpRequest;
import com.severusnguyen.ohaha.service.LoginService;
import com.severusnguyen.ohaha.service.imp.LoginServiceImp;
import com.severusnguyen.ohaha.util.JwtUtilHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.SecretKey;
import java.util.Base64;

@CrossOrigin("*") //sử dụng khi bị lỗi CORS policy
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp; //55 -1832

    @Autowired
    JwtUtilHelper jwtUtilHelper;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password){
        ResponseData responseData = new ResponseData();

//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println(encrypted);

        if (loginServiceImp.checkLogin(username, password)) {
            //nếu checkLogin = true trả token cho người dùng
            String token = jwtUtilHelper.generateToken(username);
            responseData.setData(token);

        } else {
            responseData.setData("");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK); //ResponseEntity<> tự động biến object thành json thông qua libary JSON
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        ResponseData responseData = new ResponseData();

        responseData.setData(loginServiceImp.addUser(signUpRequest));

        return new ResponseEntity<>(responseData, HttpStatus.OK); //ResponseEntity<> tự động biến object thành json thông qua libary JSON
    }

}
