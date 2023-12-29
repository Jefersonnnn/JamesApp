package com.jm.jamesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
//TODO: Interceptar criação da tabela para adicionar prefixo tb_ automaticamente
@Table(name = "TB_CUSTOMERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"cpfCnpj", "user_id"})})
public class CustomerModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne
    private UserModel user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 14)
    private String cpfCnpj;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<TransactionModel> transactions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "customers")
    private Set<GroupBillModel> groupBills = new HashSet<>();

    public CustomerModel() {}

    public CustomerModel(UserModel user, String name, String cpfCnpj) {
        this.user = user;
        this.name = name;
        this.cpfCnpj = cpfCnpj;
    }

    public Set<GroupBillModel> getGroupBills() {
        return groupBills;
    }

    public Set<TransactionModel> getTransactions() {
        return transactions;
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
