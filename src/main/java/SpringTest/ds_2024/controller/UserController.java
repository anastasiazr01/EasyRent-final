package SpringTest.ds_2024.controller;


import SpringTest.ds_2024.entity.Owner;
import SpringTest.ds_2024.entity.Roles;
import SpringTest.ds_2024.entity.Tenant;
import SpringTest.ds_2024.entity.User;
import SpringTest.ds_2024.repository.RolesRepository;
import SpringTest.ds_2024.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private RolesRepository roleRepository;

    public UserController(UserService userService, RolesRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String register(Model model) {
        System.out.println("registration starting");
        return "auth/register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam("selectedRole") String selectedRole){
        System.out.println("user data redirection");
//        try {
            if ("TENANT".equalsIgnoreCase(selectedRole)) {
                return "redirect:/tenant/register";
            } else if ("OWNER".equalsIgnoreCase(selectedRole)) {
                return "redirect:/owner/register";
            } else {
                throw new IllegalArgumentException("Invalid role selected");
            }

    }

    @GetMapping("/user/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Integer user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "edit_user";
    }

    @PostMapping("/user/{user_id}")
    public String saveStudent(@PathVariable Integer user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Roles role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Roles role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }


}
