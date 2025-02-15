package SpringTest.ds_2024.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
    private int roleId;

    @Column(length = 20)
    private String roleName;

    public Roles(String roleName) {
        this.roleName = roleName;
    }

    public Roles() {
    }

    public void addPermission(String permission) {
        System.out.println("Permission '" + permission + "' added to role '" + roleName + "'.");
    }

    public void removePermission(String permission) {
        System.out.println("Permission '" + permission + "' removed from role '" + roleName + "'.");
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return this.roleName;
    }
}
