package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public String displayAllUser(Model model) {
        model.addAttribute("users", userServiceImp.getUsersList());
        return "admin/admin";
    }

    @GetMapping("/show")
    public String showUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userServiceImp.getUser(id));
        return "admin/adminIndex";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @RequestParam(required = false) String roleAdmin) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        userServiceImp.addUser(user, roleAdmin);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userServiceImp.getUser(id));
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String update(@RequestParam("id") int id, @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, String roleAdmin) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userServiceImp.editUser(id, user, roleAdmin);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userServiceImp.deleteUser(id);
        return "redirect:/admin";
    }
}
