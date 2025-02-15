package SpringTest.ds_2024.entity;

import groovyjarjarasm.asm.commons.Remapper;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentalId;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(nullable = false)
    private LocalDate rentalDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private RentalStatus status ;  // PENDING, ACCEPTED, REJECTED

    public Rental(Property property) {
        this.property = property;
        this.rentalDate = LocalDate.now();
    }

    public Rental() {

    }

    // Getters and Setters
    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }


    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public LocalDate getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }

    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }
}
