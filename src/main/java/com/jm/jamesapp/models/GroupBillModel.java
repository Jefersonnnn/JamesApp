package com.jm.jamesapp.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "TB_GROUP_BILLS", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"})})
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
    private BillingFrequency billingFrequency;
    @Column(nullable = false)
    private Integer dueDateDay;

    @Column(nullable = false)
    private Integer dueDateHour;

    private String description;

    @ManyToMany(mappedBy = "groupBills")
    private Set<CustomerModel> customers;

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

    public Integer getDueDateDay() {
        return dueDateDay;
    }

    public void setDueDateDay(Integer dueDateDay) {
        this.dueDateDay = dueDateDay;
    }

    public Integer getDueDateHour() {
        return dueDateHour;
    }

    public void setDueDateHour(Integer dueDateHour) {
        this.dueDateHour = dueDateHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
