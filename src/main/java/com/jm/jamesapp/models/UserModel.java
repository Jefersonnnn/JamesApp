package com.jm.jamesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.jamesapp.dtos.requests.UserRequestRecordDto;
import com.jm.jamesapp.utils.constraints.enums.UserRole;
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
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<CustomerModel> customers;

    @OneToMany(mappedBy = "owner")
    private List<GroupBillModel> groupBills;

    //Todo: Adicionar a lista de Transações e talvez adicionar um método para retornar o saldo ou recalcular o saldo
    // com base nas transações feitas (indexação?)?

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public UserModel(){

    }

    public UserModel(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserModel(UserRequestRecordDto data) {
        this.username = data.username();
        this.email = data.email();
        this.password = data.password();
        this.role = data.role();
    }


    public List<CustomerModel> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerModel> customers) {
        this.customers = customers;
    }

    public List<GroupBillModel> getGroupBills() {
        return groupBills;
    }

    public void setGroupBills(List<GroupBillModel> groupBills) {
        this.groupBills = groupBills;
    }

    public void setUsername(String username) {
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
        // Todo: Melhorar depois
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
