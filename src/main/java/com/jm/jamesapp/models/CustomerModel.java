package com.jm.jamesapp.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "TB_CUSTOMERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"cpfCnpj", "user_id"})})
public class CustomerModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private UserModel user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 14)
    private String cpfCnpj;

    @ManyToMany
    @JoinTable(
            name = "TB_CUSTOMERS_GROUPBILLS",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "groupbill_id")
    )
    private Set<GroupBillModel> groupBills;

    public CustomerModel() {}

    public CustomerModel(UserModel user, String name, String cpfCnpj) {
        this.user = user;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
    }

    // TODO: TIRAR GET/SET
    public Set<GroupBillModel> getGroupBills() {
        return groupBills;
    }

    public void setGroupBills(Set<GroupBillModel> groupBills) {
        this.groupBills = groupBills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
