package SpringTest.ds_2024.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int propertyId;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private double price;

    @Column
    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.PENDING;

    @Column
    private LocalDate registrationDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Property(String type, String name, String description, String address, double price,
                    PropertyStatus status) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.address = address;
        this.price = price;
        this.status = status == null ? PropertyStatus.PENDING : status;  // Fallback if null
        this.registrationDate = LocalDate.now();
    }

    public Property() {
        this.status = PropertyStatus.PENDING;
    }

    // Getters and Setters
    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }


    @Override
    public String toString() {
        return "Property{" +
                "propertyId=" + propertyId +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", status=" + (status != null ? status.name() : "null") +
                ", registrationDate=" + registrationDate +
                '}';
    }

}
