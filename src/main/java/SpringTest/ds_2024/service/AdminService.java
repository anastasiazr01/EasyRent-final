package SpringTest.ds_2024.service;

import SpringTest.ds_2024.entity.Admin;
import SpringTest.ds_2024.entity.Roles;
import SpringTest.ds_2024.repository.AdminRepository;
import SpringTest.ds_2024.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private RolesRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public AdminService(AdminRepository adminRepository , RolesRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin saveAdmin(Admin admin) {
        System.out.println("saving admin");
        String passwd= admin.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        admin.setPassword(encodedPassword);

        Roles role = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Roles> roles = new HashSet<>();
        roles.add(role);
        admin.setRoles(roles);

        System.out.println("Saved user with ID: " + admin.getId());
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }
}
