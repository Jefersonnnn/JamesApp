package com.jm.jamesapp.models.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jm.jamesapp.models.BaseModel;
import com.jm.jamesapp.models.CustomerModel;
import com.jm.jamesapp.models.transaction.enums.TransactionOrigin;
import com.jm.jamesapp.models.transaction.enums.TransactionType;
import com.jm.jamesapp.models.user.UserModel;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TB_TRANSACTIONS")
public class TransactionModel extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Instant paymentDate;
    @ManyToOne
    private UserModel user;
    @ManyToOne
    private CustomerModel customer;
    private BigDecimal amount;;
    private String description;
    private TransactionOrigin origin;

    public TransactionModel() {
    }

    public TransactionModel(TransactionType type, Instant paymentDate, UserModel user, CustomerModel customer, BigDecimal amount, String description, TransactionOrigin origin) {
        this.type = type;
        this.paymentDate = paymentDate;
        this.user = user;
        this.customer = customer;
        this.amount = amount;
        this.description = description;
        this.origin = origin;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(TransactionOrigin origin) {
        this.origin = origin;
    }
}
