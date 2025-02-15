package SpringTest.ds_2024.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Logged in user: " + authentication.getName());
        List<String> roleNames = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        System.out.println("Roles: " + roleNames);

        model.addAttribute("roles", roleNames);
        return "index";
    }


}
