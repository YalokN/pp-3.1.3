package ru.kata.spring.boot_security.demo.bdInitialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Component
public class BdInitialize {
    private UserService userService = new UserServiceImpl();

    private RoleService roleService = new RoleServiceImpl();

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleServiceImpl) {
        this.roleService = roleServiceImpl;
    }

    public BdInitialize(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        User user = new User("user", "$2a$12$cPCYPUBPa0bpY63CVNAaiOId.R8ws15FT4GHY.fV1dldU5tJwFSdW");
        User admin = new User("admin", "$2a$12$uOU6F.094UR7vRe15bOMlOY/o8rMCI8aJQnjm839JFrzcy7LK5E5G");
        User adminAndUser = new User("adminAndUser", "$2a$12$6xWeRhuOEe3ZK4zPq0gUG.oqfucObuFck4HBZsMWHTrsCrQ2tIe8S");

        user.addRole(roleUser);
        admin.addRole(roleAdmin);
        adminAndUser.addRole(roleUser);
        adminAndUser.addRole(roleAdmin);

        roleServiceImpl.saveRole(roleUser);
        roleServiceImpl.saveRole(roleAdmin);

        userServiceImpl.saveUser(user);
        userServiceImpl.saveUser(admin);
        userServiceImpl.saveUser(adminAndUser);

        System.out.println();
    }
}
