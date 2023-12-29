package com.jm.jamesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.jamesapp.dtos.requests.ApiUserRequestDto;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_USERS")
public class UserModel extends BaseModel implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<CustomerModel> customers;

    @OneToMany(mappedBy = "user")
    private List<GroupBillModel> groupBills;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public UserModel(){}

    public UserModel(String name, String username, String email, String password, UserRole role) {
        this.name = name;
        setUsername(this.username = username);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserModel(ApiUserRequestDto data) {
        this.name = data.name();
        setUsername(data.username());
        this.email = data.email();
        this.password = data.password();
        this.role = data.role();
    }

    public enum UserRole {
        ADMIN("admin"),
        USER("user");

        private String role;

        UserRole(String role){
            this.role = role;
        }

        public String getRole(){
            return role;
        }
    }


    public List<CustomerModel> getCustomers() {
        return customers;
    }

    public List<GroupBillModel> getGroupBills() {
        return groupBills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        username = username.replace(" ", "_");
        username = username.toLowerCase();
        this.username = username;
    }
    @Override
    public String getUsername() {
        return username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
