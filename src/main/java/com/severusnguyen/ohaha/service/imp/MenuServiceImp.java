package com.severusnguyen.ohaha.service.imp;

import org.springframework.web.multipart.MultipartFile;

public interface MenuServiceImp {
     boolean createMenu(MultipartFile file, String title, String time_ship, String is_freeship, String price, int cate_id);
}
