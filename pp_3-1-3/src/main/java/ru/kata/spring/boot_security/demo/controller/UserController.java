package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

/*        Collection<Role> roleUserCol = new ArrayList<>();
        Collection<Role> roleAdminCol = new ArrayList<>();
        Collection<Role> roleAdminAndUserCol = new ArrayList<>();
        roleUserCol.add(roleUser);
        roleAdminCol.add(roleAdmin);
        roleAdminAndUserCol.add(roleUser);
        roleAdminAndUserCol.add(roleAdmin);*/

        User user = new User("user", "$2a$12$cPCYPUBPa0bpY63CVNAaiOId.R8ws15FT4GHY.fV1dldU5tJwFSdW");
        User admin = new User("admin", "$2a$12$uOU6F.094UR7vRe15bOMlOY/o8rMCI8aJQnjm839JFrzcy7LK5E5G");
        User adminAndUser = new User("adminAndUser", "$2a$12$6xWeRhuOEe3ZK4zPq0gUG.oqfucObuFck4HBZsMWHTrsCrQ2tIe8S");

        user.addRole(roleUser);
        admin.addRole(roleAdmin);
        adminAndUser.addRole(roleUser);
        adminAndUser.addRole(roleAdmin);

        roleService.saveRole(roleUser);
        roleService.saveRole(roleAdmin);

        userService.saveUser(user);
        userService.saveUser(admin);
        userService.saveUser(adminAndUser);

        System.out.println();

    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/user")
    public String authenticated() {
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
        userService.saveUser(user);
        return "redirect:/admin/index";
    }

    @GetMapping(value = "admin/del")
    public String delUser(ModelMap model) {
        model.addAttribute("userDel", new User());
        return "admin/del";
    }

    @DeleteMapping("/admin/index")
    public String deletedUser(@ModelAttribute("userDel") User userDel) {
        userService.deleteUserById(userDel.getId());
        return "redirect:/admin/index";
    }

    @GetMapping(value = "admin/edit")
    public String editUser(ModelMap model) {
        model.addAttribute("userEdit", new User());
        return "admin/edit";
    }

    @PatchMapping("/admin/index")
    public String editedUser(@ModelAttribute("userEdit") User userEdit) {
        userService.changeUser(userEdit);
        return "redirect:/admin/index";
    }
}
