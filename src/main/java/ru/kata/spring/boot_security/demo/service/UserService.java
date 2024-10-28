package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserService {

    List<User> getUsersList();
    User getUser(int id);
    void addUser(User user,String roleAdmin);
    void editUser(int id, User user, String roleAdmin);
    void deleteUser(int id);

}
