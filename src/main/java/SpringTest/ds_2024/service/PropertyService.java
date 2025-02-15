package SpringTest.ds_2024.service;

import SpringTest.ds_2024.entity.*;
import SpringTest.ds_2024.repository.OwnerRepository;
import SpringTest.ds_2024.repository.PropertyRepository;
import SpringTest.ds_2024.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final RentalRepository rentalRepository;

    private final PropertyRepository propertyRepository;
    private final OwnerService ownerService;
    private final OwnerRepository ownerRepository;

    public PropertyService(RentalRepository rentalRepository, PropertyRepository propertyRepository, OwnerRepository ownerRepository, OwnerService ownerService) {
        this.rentalRepository = rentalRepository;
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
        this.ownerService = ownerService;
    }




    @Transactional
    public void saveProperty(Property property) {
        property.setRegistrationDate(LocalDate.now());
        propertyRepository.save(property);
    }

    @Transactional
    public void approveApplication(int id) {
        Property application = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(PropertyStatus.AVAILABLE);
    }

    @Transactional
    public void rejectApplication(int id) {
        Property application = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        propertyRepository.delete(application);
    }

    @Transactional
    public void saveRental(int id,Tenant tenant) {
        Property property = propertyRepository.findById(id).get();
        Rental rental = new Rental(property);
        rental.setStatus(RentalStatus.RENT);
        rental.setTenant(tenant);
        rentalRepository.save(rental);
        property.setTenant(tenant);
    }
    @Transactional
    public void saveView(int id,Tenant tenant) {
        Property property = propertyRepository.findById(id).get();
        Rental rental = new Rental(property);
        rental.setStatus(RentalStatus.VIEW);
        rental.setTenant(tenant);
        rentalRepository.save(rental);
    }


    @Transactional
    public void approveRentalApplication(int id) {

        Rental application = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        RentalStatus status = application.getStatus();
        int propertyId = application.getProperty().getPropertyId();
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        if(status == RentalStatus.VIEW){
                //notify tenant
                rentalRepository.deleteById(id);
        }else {
                //notify tenant
                rentalRepository.deleteById(id);
                rentalRepository.deleteByProperty_PropertyId(propertyId);
                property.setStatus(PropertyStatus.RENTED);
                propertyRepository.save(property);
        }
    }

    @Transactional
    public void rejectRentalApplication(int id) {
        Rental application = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        RentalStatus status = application.getStatus();

        if(status == RentalStatus.VIEW){
            rentalRepository.deleteById(id);
        }else {
            rentalRepository.deleteById(id);
        }
    }







    @Transactional
    public List<Rental> getRentalApplications() {
        return rentalRepository.findAll();
    }

    @Transactional
    public List<Property> getProperties() {
        return propertyRepository.findAll();
    }

    @Transactional
    public List<Property> getAvailableProperties() {
        return propertyRepository.findByStatus(PropertyStatus.AVAILABLE);
    }

    @Transactional
    public List<Property> getPendingProperties() {
        return propertyRepository.findByStatus(PropertyStatus.PENDING);
    }

    @Transactional
    public List<Property> getRentedProperties() {
        return propertyRepository.findByStatus(PropertyStatus.RENTED);
    }

    @Transactional
    public Property getPropertyById(Integer propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    }

    public List<Property> getPropertiesByOwnerId(int ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
    public List<Rental> getRentalsByOwnerId(int ownerId) {

        return rentalRepository.findByProperty_Owner_Id(ownerId);

    }

    @Transactional
    public void deleteProperty(Integer propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    public List<Property> searchProperties(String type, Double minPrice, Double maxPrice) {
        if (type == null && minPrice == null && maxPrice == null) {
            return getAvailableProperties();
        }
        return propertyRepository.searchProperties(type, minPrice, maxPrice,PropertyStatus.AVAILABLE);
    }
}
