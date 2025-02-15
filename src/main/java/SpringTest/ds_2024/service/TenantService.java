package SpringTest.ds_2024.service;

import SpringTest.ds_2024.entity.Roles;
import SpringTest.ds_2024.entity.Tenant;
import SpringTest.ds_2024.entity.User;
import SpringTest.ds_2024.entity.Rental;
import SpringTest.ds_2024.repository.TenantRepository;
import SpringTest.ds_2024.repository.RolesRepository;
import SpringTest.ds_2024.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TenantService {

    private  TenantRepository tenantRepository;
    private RolesRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RentalRepository rentalRepository;

    public TenantService(TenantRepository tenantRepository ,RolesRepository roleRepository, BCryptPasswordEncoder passwordEncoder, RentalRepository rentalRepository) {
        this.tenantRepository = tenantRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.rentalRepository = rentalRepository;
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Integer id) {
        return tenantRepository.findById(id).orElse(null);
    }

    public Tenant saveTenant(Tenant tenant) {
        System.out.println("saving tenant");
        String passwd= tenant.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        tenant.setPassword(encodedPassword);

        Roles role = roleRepository.findByRoleName("ROLE_TENANT")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Roles> roles = new HashSet<>();
        roles.add(role);
        tenant.setRoles(roles);

        System.out.println("Saved user with ID: " + tenant.getId());
        return tenantRepository.save(tenant);
    }

    public List<Rental> getRentalsByTenantId(int tenantId) {
        return rentalRepository.findByProperty_Tenant_Id(tenantId);
    }


    public void deleteTenant(Integer id) {
        tenantRepository.deleteById(id);
    }
}