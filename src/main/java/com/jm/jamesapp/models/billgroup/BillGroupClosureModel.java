package com.jm.jamesapp.models.billgroup;

import com.jm.jamesapp.models.BaseModel;
import com.jm.jamesapp.models.CustomerModel;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_BILL_GROUP_CLOSURE")
public class BillGroupClosureModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "bill_group_id")
    private BillGroupModel billGroup;
    private LocalDate closureDate;
    private BigDecimal value;
    @ManyToMany
    @JoinTable(
            name = "bill_group_closure_customer",
            joinColumns = @JoinColumn(name = "bill_group_closure_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<CustomerModel> customers = new HashSet<>();

    public BillGroupClosureModel(BillGroupModel billGroup, LocalDate closureDate, BigDecimal value, Set<CustomerModel> customers) {
        this.billGroup = billGroup;
        this.closureDate = closureDate;
        this.value = value;
        this.customers = customers;
    }

    public BillGroupClosureModel() {

    }

    public BillGroupModel getBillGroup() {
        return billGroup;
    }

    public void setBillGroup(BillGroupModel billGroup) {
        this.billGroup = billGroup;
    }

    public LocalDate getCreated() {
        return closureDate;
    }

    public void setCreated(LocalDate created) {
        this.closureDate = created;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Set<CustomerModel> getCustomers() {
        return customers;
    }

}
