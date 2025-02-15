package SpringTest.ds_2024.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Owner extends User{

        @Column
        private String firstName;

        @Column
        private String lastName;

        @Column
        private String phoneNumber;

        @OneToMany(mappedBy = "owner", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )
        private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

        public Owner(String username, String password, String email, Set<Roles> roles,
                     String firstName, String lastName, String phoneNumber, List<Property> properties) {
            super(username, password, email, roles);
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            //this.properties = properties;
        }


    public Owner(String username, String password, String email,
                 String firstName, String lastName, String phoneNumber) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Owner() {
    }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
            public String toString() {
                return "Owner{" +
                        "firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", phoneNumber=" + phoneNumber +
                        '}';
            }
}

