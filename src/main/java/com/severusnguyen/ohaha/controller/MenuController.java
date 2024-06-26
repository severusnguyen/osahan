package com.severusnguyen.ohaha.controller;

import com.severusnguyen.ohaha.payload.ResponseData;
import com.severusnguyen.ohaha.service.imp.FileServiceImp;
import com.severusnguyen.ohaha.service.imp.MenuServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuServiceImp menuServiceImp;

    @Autowired
    FileServiceImp fileServiceImp;

    @PostMapping()
    public ResponseEntity<?> createMenu(
            @RequestParam MultipartFile file,
            @RequestParam String title,
            @RequestParam String is_freeship,
            @RequestParam  String time_ship,
            @RequestParam String price,
            @RequestParam int cate_id ){

        ResponseData responseData = new ResponseData(); //Để người dùng biết thành công hay thất bại
        boolean isSuccess = menuServiceImp.createMenu(file, title, is_freeship, time_ship, price, cate_id);
        responseData.setData(isSuccess);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filenam:.+}")
    public ResponseEntity<?> getFileRestaurant(@PathVariable String filenam){

        Resource resource = fileServiceImp.loadFile(filenam);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
