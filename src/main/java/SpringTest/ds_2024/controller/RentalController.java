package SpringTest.ds_2024.controller;

import SpringTest.ds_2024.entity.*;
//import SpringTest.ds_2024.entity.Rental;
//import SpringTest.ds_2024.entity.RentalApplication;
import SpringTest.ds_2024.service.PropertyService;
import SpringTest.ds_2024.service.SecurityService;
//import SpringTest.ds_2024.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("rental")
public class RentalController {

    private final PropertyService propertyService;
    private final SecurityService securityService;


    public RentalController(PropertyService propertyService, SecurityService securityService) {
        this.propertyService = propertyService;
        this.securityService = securityService;
    }

    @GetMapping("")
    public String showAvailableProperties(Model model) {
        List<Property> availableProperties = propertyService.getAvailableProperties();
        model.addAttribute("properties", availableProperties);
        return "rental/rentals";
    }


    @GetMapping("/rent/{propertyId}")
    public String rentProperty(@PathVariable Integer propertyId, Model model) {
        Tenant tenant = (Tenant) securityService.getAuthenticatedTenant();
        if (tenant == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/rental";
        }
        propertyService.saveRental(propertyId,tenant);
        return "redirect:/rental";
    }



    @GetMapping("/view/{propertyId}")
    public String viewProperty(@PathVariable Integer propertyId, Model model) {
        Tenant tenant = (Tenant) securityService.getAuthenticatedTenant();
        if (tenant == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/rental";
        }
        propertyService.saveView(propertyId,tenant);
        return "redirect:/rental";
    }


    @GetMapping("/application-list/all")
    public String showRentalApplications(Model model) {
        Owner owner = (Owner) securityService.getAuthenticatedOwner();
        if (owner == null) {
            model.addAttribute("msg", "User not found!");
            return "redirect:/owner";
        }

        List<Rental> rentals = propertyService.getRentalsByOwnerId(owner.getId());

        model.addAttribute("rentals", rentals);
        rentals.forEach(System.out::println);
        return "rental/application-list";
    }

    @PostMapping("/approve/{id}")
    public String approveRentalApplication(@PathVariable int id) {

        propertyService.approveRentalApplication(id);
        return "redirect:/rental/application-list/all";
    }

    @PostMapping("/reject/{id}")
    public String rejectRentalApplication(@PathVariable int id) {
        propertyService.rejectRentalApplication(id);

        return "redirect:/rental/application-list/all";
    }



    @GetMapping("/search")
    public String search(@RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "minPrice", required = false) Double minPrice,
                         @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                         Model model) {
        System.out.println("Search method is being called! Type: " + type + ", MinPrice: " + minPrice + ", MaxPrice: " + maxPrice);
        List<Property> properties = propertyService.searchProperties(type, minPrice, maxPrice);

        model.addAttribute("properties", properties);
        model.addAttribute("type", type);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "rental/search-results";
    }

}