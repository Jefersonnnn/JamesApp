package com.jm.jamesapp.models.billgroup;

import com.jm.jamesapp.models.BaseModel;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;
import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_GROUP_BILLS", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
public class BillGroupModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    private UserModel user;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingFrequency billingFrequency;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "tb_groupbill_customers",
            joinColumns = @JoinColumn(name = "groupbill_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id")
    )
    private Set<CustomerModel> customers = new HashSet<>();


    public Set<CustomerModel> getCustomers() {
        return customers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(BillingFrequency billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
