package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsersList();
    User getUser(int id);
    void addUser(User user);
    void editUser(int id, User user);
    void deleteUser(int id);

}
