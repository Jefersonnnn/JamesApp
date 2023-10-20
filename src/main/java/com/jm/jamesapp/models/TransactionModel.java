package com.jm.jamesapp.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TB_TRANSACTIONS")
public class TransactionModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private TypeTransaction type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @ManyToOne
    private UserModel owner;
    @ManyToOne
    private CustomerModel customer;
    private BigDecimal amount;
    @Column(nullable = false)
    private StatusTransaction status;
    private String description;
    private String cancelDescription;

    public enum TypeTransaction {
        PAYMENT_RECEIVED("Payment received"),
        PAID_GROUPBILL("Paid groupbill");

        private final String label;

        TypeTransaction(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

    public enum StatusTransaction {
        COMPLETED("Completed"),
        CANCELED("Canceled"),
        ERROR("Error");

        private final String label;

        StatusTransaction(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }


    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public StatusTransaction getStatus() {
        return status;
    }

    public void setStatus(StatusTransaction status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }
}
