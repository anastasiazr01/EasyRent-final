package SpringTest.ds_2024.repository;

import SpringTest.ds_2024.entity.Owner;
import SpringTest.ds_2024.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Optional<Owner> findByUsername(String username);

}
