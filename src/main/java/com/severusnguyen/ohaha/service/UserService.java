package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.dto.UserDTO;
import com.severusnguyen.ohaha.entity.Users;
import com.severusnguyen.ohaha.repository.UserRepository;
import com.severusnguyen.ohaha.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {

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
}
