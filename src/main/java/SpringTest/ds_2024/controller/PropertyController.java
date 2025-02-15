package SpringTest.ds_2024.controller;

import SpringTest.ds_2024.entity.Owner;
import SpringTest.ds_2024.entity.Property;
import SpringTest.ds_2024.entity.PropertyStatus;
import SpringTest.ds_2024.service.OwnerService;
import SpringTest.ds_2024.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("property")
public class PropertyController {

    private final PropertyService propertyService;
    private final OwnerService ownerService;

    public PropertyController(PropertyService propertyService, OwnerService ownerService) {
        this.propertyService = propertyService;
        this.ownerService = ownerService;
    }


    @GetMapping("")
    public String showProperties(Model model) {
        List<Property> properties = new ArrayList<>();
        properties.addAll(propertyService.getAvailableProperties());
        properties.addAll(propertyService.getRentedProperties());
        model.addAttribute("properties", properties);
        return "property/properties";
    }

    @GetMapping("/new")
    public String addProperty(Model model) {
        Property property = new Property();
        model.addAttribute("property", property);
        return "property/property";
    }

    @PostMapping("/new")
    public String saveProperty(@ModelAttribute Property property, Principal principal) {
//        propertyApplicationService.createAndSavePropertyApplication(property);
        String username = principal.getName();
        Owner owner = ownerService.findByUsername(username);

        if (owner == null) {
            throw new IllegalStateException("Owner not found");
        }

        property.setOwner(owner);

        property.setStatus(PropertyStatus.PENDING);  // Initially set the property status as pending
        propertyService.saveProperty(property);
        return "redirect:/property";
    }

    @PostMapping("/approve/{id}")
    public String approveApplication(@PathVariable int id) {
        propertyService.approveApplication(id);  // Approve the application (only change status)
        return "redirect:/property";
    }

    @PostMapping("/reject/{id}")
    public String rejectApplication(@PathVariable int id) {
        propertyService.rejectApplication(id);
        return "redirect:/property/property-application/all";
    }

    @GetMapping("/property-application/all")
    public String showAllApplications(Model model) {
//        List<PropertyApplication> applications = propertyApplicationService.getAllPendingApplications();
        List<Property> properties = propertyService.getPendingProperties();
        model.addAttribute("properties", properties);
        return "property/property-application";
    }

    @GetMapping("/{propertyId}")
    public String viewProperty(@PathVariable Integer propertyId, Model model) {
        Property property = propertyService.getPropertyById(propertyId);
        model.addAttribute("property", property);
        return "property/view";
    }


    @GetMapping("/delete/{propertyId}")

    public String deleteProperty(@PathVariable Integer propertyId, Model model) {
        try {
            propertyService.deleteProperty(propertyId);
            model.addAttribute("msg", "Tenant deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("msg", "Error deleting tenant: " + e.getMessage());
        }
        return "redirect:/property";
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

        return "property/search-results";
    }

}
