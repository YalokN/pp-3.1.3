package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService userService = new UserServiceImpl();

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap model) {
        model.addAttribute("userInfo", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping(value = "admin/index")
    public String findAll(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("messages", users);
        return "admin/index";
    }

    @GetMapping(value = "admin/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/admin/index")
    public String createNewUser(@ModelAttribute("user") User user) {
        String pablo = passwordEncoder.encode(user.getPassword());
        user = new User(user.getUsername(), pablo);
        userService.saveUser(user);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/admin/{id}")
    public String deletedUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/index";
    }

    @GetMapping(value = "admin/{id}")
    public String editUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("userEdit", userService.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/admin/index")
    public String editedUser(@ModelAttribute("userEdit") User userEdit) {
        String pablo = passwordEncoder.encode(userEdit.getPassword());
        userEdit = new User(userEdit.getId(), userEdit.getUsername(), pablo);
        userService.changeUser(userEdit);
        return "redirect:/admin/index";
    }
}
