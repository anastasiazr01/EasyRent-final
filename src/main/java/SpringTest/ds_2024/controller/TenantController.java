package SpringTest.ds_2024.controller;

import SpringTest.ds_2024.entity.Property;
import SpringTest.ds_2024.entity.Rental;
import SpringTest.ds_2024.entity.Tenant;
import SpringTest.ds_2024.service.TenantService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import SpringTest.ds_2024.service.SecurityService;
import SpringTest.ds_2024.service.PropertyService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("tenant")
public class TenantController {

    private final TenantService tenantService;
    private final SecurityService securityService;
    private final PropertyService propertyService;

    public TenantController(TenantService tenantService, SecurityService securityService, PropertyService propertyService) {
        this.tenantService = tenantService;
        this.securityService = securityService;
        this.propertyService = propertyService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Tenant tenant = (Tenant) securityService.getAuthenticatedTenant();
        if (tenant == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/tenant";
        }

        List<Rental> rentals = tenantService.getRentalsByTenantId(tenant.getId());
        model.addAttribute("rentals", rentals);
        model.addAttribute("tenant", tenant);
        return "tenant/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        Tenant tenant = (Tenant) securityService.getAuthenticatedTenant();
        if (tenant == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/tenant";
        }
        model.addAttribute("tenant", tenant);
        return "tenant/edit-profile";
    }


    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("tenant") Tenant updatedTenant, Model model) {
        Tenant tenant = (Tenant) securityService.getAuthenticatedTenant();
        if (tenant == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/tenant";
        }

        if (!tenant.getId().equals(updatedTenant.getId())) {
            model.addAttribute("msg", "You are not authorized to update this profile.");
            return "redirect:/tenant";
        }

        tenant.setFirstName(updatedTenant.getFirstName());
        tenant.setLastName(updatedTenant.getLastName());
        tenant.setEmail(updatedTenant.getEmail());
        tenant.setPhoneNumber(updatedTenant.getPhoneNumber());
        tenant.setPassword(updatedTenant.getPassword());
        tenant.setUsername(updatedTenant.getUsername());

        tenantService.saveTenant(tenant);
        model.addAttribute("msg", "Profile updated successfully!");
        return "redirect:/tenant/profile";
    }

    @GetMapping("")
    @Secured("ROLE_ADMIN")
    public String showTenants(Model model) {
        model.addAttribute("tens", tenantService.getAllTenants());
        return "tenant/tenants";
    }
    @GetMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteTenant(@PathVariable Integer id, Model model) {
        try {
            tenantService.deleteTenant(id);
            model.addAttribute("msg", "Tenant deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("msg", "Error deleting tenant: " + e.getMessage());
        }
        return "redirect:/tenant";
    }

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    public String showTenant(@PathVariable Integer id, Model model) {
        Tenant tenant = tenantService.getTenantById(id);
        if (tenant == null) {
            model.addAttribute("msg", "Tenant not found!");
            return "redirect:/tenant";
        }
        model.addAttribute("tenant", tenant); // Use `tenant` consistently
        return "tenant/details";
    }

    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    public String addTenant(Model model) {
        System.out.println("new empty tenant created");
        model.addAttribute("tenant", new Tenant());
        return "tenant/tenant";
    }

    @PostMapping("/new")
    @Secured("ROLE_ADMIN")
    public String saveTenant(@ModelAttribute("tenant") Tenant tenant, Model model) {
        System.out.println("saving tenant?");
        tenantService.saveTenant(tenant);
        model.addAttribute("tens", tenantService.getAllTenants());
        return "tenant/tenants";
    }

    @GetMapping("/register")
    public String tenantRegister(Model model) {
        model.addAttribute("tenant", new Tenant());
        System.out.println("new empty tenant created");
        return "tenant/register";
    }

    @PostMapping("/register")
    public String saveTenantRegister(@ModelAttribute("tenant") Tenant tenant, Model model) {
        System.out.println("saving tenant?");
        tenantService.saveTenant(tenant);
        model.addAttribute("tens", tenantService.getAllTenants());
        return "redirect:/login";
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String editTenant(@PathVariable Integer id, Model model) {
        Tenant tenant = tenantService.getTenantById(id);
        if (tenant == null) {
            model.addAttribute("msg", "Tenant not found!");
            return "redirect:/tenant";
        }
        model.addAttribute("tenant", tenant);
        return "tenant/edit";
    }


    @PostMapping("/edit/{id}")
    @Secured("ROLE_ADMIN")
    public String updateTenant(@PathVariable Integer id, @ModelAttribute("tenant") Tenant updatedTenant, Model model) {
        Tenant existingTenant = tenantService.getTenantById(id);
        if (existingTenant == null) {
            model.addAttribute("msg", "Tenant not found!");
            return "redirect:/tenant";
        }

        existingTenant.setFirstName(updatedTenant.getFirstName());
        existingTenant.setLastName(updatedTenant.getLastName());
        existingTenant.setEmail(updatedTenant.getEmail());
        existingTenant.setPhoneNumber(updatedTenant.getPhoneNumber());
        existingTenant.setPassword(updatedTenant.getPassword());
        existingTenant.setUsername(updatedTenant.getUsername());

        tenantService.saveTenant(existingTenant);
        model.addAttribute("msg", "Tenant updated successfully!");
        return "redirect:/tenant";
    }

}