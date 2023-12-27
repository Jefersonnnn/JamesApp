package com.jm.jamesapp.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "TB_CUSTOMERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"cpfCnpj", "owner_id"})})
public class CustomerModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private UserModel owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 14)
    private String cpfCnpj;
    private BigDecimal balance;

    @ManyToMany
    @JoinTable(
            name = "TB_CUSTOMERS_GROUPBILLS",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "groupbill_id")
    )
    private Set<GroupBillModel> groupBills;

    public CustomerModel() {}

    public CustomerModel(UserModel owner, String name, String cpfCnpj) {
        this.owner = owner;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.balance = new BigDecimal(0);
    }

    public Set<GroupBillModel> getGroupBills() {
        return groupBills;
    }

    public void setGroupBills(Set<GroupBillModel> groupBills) {
        this.groupBills = groupBills;
    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }
}
