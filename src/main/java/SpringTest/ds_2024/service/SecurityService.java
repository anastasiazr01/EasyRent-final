package SpringTest.ds_2024.service;

import SpringTest.ds_2024.entity.Owner;
import SpringTest.ds_2024.entity.Tenant;
import SpringTest.ds_2024.entity.User;
import SpringTest.ds_2024.repository.TenantRepository;
import SpringTest.ds_2024.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private TenantRepository tenantRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public SecurityService(TenantRepository tenantRepository, OwnerRepository ownerRepository) {
        this.tenantRepository = tenantRepository;
        this.ownerRepository = ownerRepository;
    }

    public Tenant getAuthenticatedTenant() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            return tenantRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Tenant not found for username: " + username));
        }

        throw new RuntimeException("Principal is not an instance of UserDetails");
    }

    public Owner getAuthenticatedOwner() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            return ownerRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Owner not found for username: " + username));
        }

        throw new RuntimeException("Principal is not an instance of UserDetails");
    }
}
