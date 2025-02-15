package SpringTest.ds_2024.repository;
import SpringTest.ds_2024.entity.Property;
import SpringTest.ds_2024.entity.PropertyStatus;
import SpringTest.ds_2024.entity.Rental;
import SpringTest.ds_2024.entity.RentalStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByProperty(Property property);
    List<Rental> findByStatus(RentalStatus status);
    List<Rental> findByTenant_Id(int tenantId);
    List<Rental> findByProperty_Owner_Id(int ownerId);
    List<Rental> findByProperty_Tenant_Id(int tenantId);

    //    @Transactional
    void deleteByProperty_PropertyId(int propertyId);
}

