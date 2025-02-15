package SpringTest.ds_2024.repository;

import SpringTest.ds_2024.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByRoleName(String roleName);

    default Roles updateOrInsert(Roles role) {
        Roles existing_role = findByRoleName(role.getRoleName()).orElse(null);
        if (existing_role != null) {
            return existing_role;
        }
        else {
            return save(role);
        }
    }
}