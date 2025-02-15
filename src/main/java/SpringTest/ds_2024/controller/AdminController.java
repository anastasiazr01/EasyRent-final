package SpringTest.ds_2024.controller;

import SpringTest.ds_2024.entity.Admin;
import SpringTest.ds_2024.service.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public String showAdmins(Model model) {
        model.addAttribute("adms", adminService.getAllAdmins());
        return "admin/admins";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{id}")
    public String showAdmin(@PathVariable Integer id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("adms", admin);
        return "admin/adminprofile";
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteAdmin(@PathVariable Integer id, Model model) {
        try {
            adminService.deleteAdmin(id);
            model.addAttribute("msg", "Tenant deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("msg", "Error deleting tenant: " + e.getMessage());
        }
        return "redirect:/admin";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String addAdmin(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/admin";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public String saveAdmin(@ModelAttribute("admin") Admin admin, Model model) {
        adminService.saveAdmin(admin);
        model.addAttribute("adms", adminService.getAllAdmins());
        return "admin/admins";
    }
}