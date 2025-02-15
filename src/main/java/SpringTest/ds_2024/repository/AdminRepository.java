package SpringTest.ds_2024.repository;

import SpringTest.ds_2024.entity.Admin;
import SpringTest.ds_2024.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Tenant> findByUsername(String username);

}
