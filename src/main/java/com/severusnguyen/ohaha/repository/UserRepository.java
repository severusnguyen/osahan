package com.severusnguyen.ohaha.repository;

import com.severusnguyen.ohaha.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> /*<tên Entity, kiểu dữ liệu khóa chính Entity> */ {
// Tại vì Repository đang cần tương tác với Users nên đặt tên là UserInterface
    /*
    * Select * from users where username = '' and password = ''
    * */
    List<Users> findByUserNameAndPassword(String username, String password);
    Users findByUserName(String userName);
}
