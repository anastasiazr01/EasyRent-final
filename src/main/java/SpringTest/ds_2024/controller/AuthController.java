package SpringTest.ds_2024.controller;

import SpringTest.ds_2024.entity.Roles;
import SpringTest.ds_2024.repository.RolesRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    RolesRepository roleRepository;

    public AuthController(RolesRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setup() {
        Roles role_user = new Roles("ROLE_USER");
        Roles role_admin = new Roles("ROLE_ADMIN");
        Roles role_tenant = new Roles("ROLE_TENANT");
        Roles role_owner = new Roles("ROLE_OWNER");

        roleRepository.updateOrInsert(role_user);
        roleRepository.updateOrInsert(role_admin);
        roleRepository.updateOrInsert(role_owner);
        roleRepository.updateOrInsert(role_tenant);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}