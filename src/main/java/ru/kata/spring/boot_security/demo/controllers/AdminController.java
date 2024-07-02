package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.util.List;

@Controller
public class AdminController {
    final UserService userService;
    final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String pageForAdmin(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "admin";
    }

    @GetMapping("/admin/add-user")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("availableRoles", roleService.findAll());

        return "add-user";
    }

    @PostMapping("/admin/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userService.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/admin/update-user")
    public String updateUser(@RequestParam("username") String username, Model model) {
        User user = userService.findByUsername(username);

        model.addAttribute("user",  user);
        model.addAttribute("availableRoles", roleService.findAll());

        return "add-user";
    }

    @GetMapping("/admin/delete-user")
    public String deleteUser(@RequestParam("username") String username) {
        userService.delete(userService.findByUsername(username));

        return "redirect:/admin";
    }
}
