package SpringTest.ds_2024.entity;

import jakarta.persistence.*;


import java.util.Set;

@Entity
public class Admin extends User  {

    public Admin(String username, String password, String email, Integer id, Set<Roles> roles) {
        super(username, password, email, roles);
    }
    public Admin(String username, String password, String email, Integer id) {
        super(username, password, email);
    }
    public Admin(){
        super();
    }


    public void viewPendingRegistrations() {
        System.out.println("Viewing pending registrations.");
    }

    public void approvePendingRegistrations() {
        System.out.println("Approving pending registrations.");
    }

    public void deleteRegistration() {
        System.out.println("Deleting registration.");
    }

    public void addRole(Roles role) {
        System.out.println("Role added: " + role.getRoleName());
    }

    public void removeRole(Roles role) {
        System.out.println("Role removed: " + role.getRoleName());
    }


}
