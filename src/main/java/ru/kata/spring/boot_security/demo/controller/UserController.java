package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public UserController(UserServiceImp userServiceImpl) {
        this.userServiceImp = userServiceImpl;
    }

    @GetMapping
    public String showUser(Model model, Principal principal) {
        User user =(User) userServiceImp.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
