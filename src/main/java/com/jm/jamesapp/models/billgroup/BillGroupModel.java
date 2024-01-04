package com.jm.jamesapp.models.billgroup;

import com.jm.jamesapp.models.BaseModel;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.billgroup.enums.BillingFrequency;
import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_BILL_GROUP", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
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

    @Column(nullable = false)
    private Integer maxClosureDay;

    @ManyToMany
    @JoinTable(
            name = "tb_bill_group_customers",
            joinColumns = @JoinColumn(name = "bill_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id")
    )
    private Set<CustomerModel> customers = new HashSet<>();

    @OneToMany(mappedBy = "billGroup")
    private Set<BillGroupClosureModel> billGroupClosures;

    public boolean shouldCloseGroup(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth() >= maxClosureDay;
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

    public Integer getMaxClosureDay() {
        return maxClosureDay;
    }

    public void setMaxClosureDay(Integer maxClosureDay) {
        this.maxClosureDay = maxClosureDay;
    }
}
