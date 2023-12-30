package com.jm.jamesapp.models;

import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "TB_GROUP_BILLS", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
public class GroupBillModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    private UserModel user;

    @Column(nullable = false)
    private BigDecimal totalPayment;

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

    public enum BillingFrequency {
        DAILY("Daily"),
        MONTHLY("Monthly"),
        ANNUALLY("Annually");

        private final String label;

        BillingFrequency(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

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

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
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
