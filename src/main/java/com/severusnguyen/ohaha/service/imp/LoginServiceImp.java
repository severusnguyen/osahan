package com.severusnguyen.ohaha.service.imp;

import com.severusnguyen.ohaha.dto.UserDTO;
import com.severusnguyen.ohaha.payload.request.SignUpRequest;

import java.util.List;

public interface LoginServiceImp {
    List<UserDTO> getAllUser();
    boolean checkLogin(String username, String password);
    boolean addUser(SignUpRequest signUpRequest);

}
