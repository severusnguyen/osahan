package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.dto.UserDTO;
import com.severusnguyen.ohaha.entity.Roles;
import com.severusnguyen.ohaha.entity.Users;
import com.severusnguyen.ohaha.payload.request.SignUpRequest;
import com.severusnguyen.ohaha.repository.UserRepository;
import com.severusnguyen.ohaha.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
//            @Qualifier("tenBean") giúp lấy ra đúng class khi mà có 2 class trùng tên, tên bean luôn là duy nhất. Nếu không đặt tên bean thì sẽ tự lấy tên class làm tên Bean
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser(){

        List<Users> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (Users users: listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName((users.getUserName()));
            userDTO.setFullName(users.getFullName());
            userDTO.setPassword(users.getPassword());

            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        //So sánh chuỗi password nhập vào chưa mã hóa và đã mã hóa trong db
        Users user = userRepository.findByUserName(username);

        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {

        Roles roles = new Roles();
        roles.setId(signUpRequest.getRoleId());

        Users users = new Users();

        users.setFullName(signUpRequest.getFullname());
        users.setUserName(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setRoles(roles);

        try {
            userRepository.save(users);

            return  true;
        } catch (Exception e){
            return  false;
        }
    }


}
