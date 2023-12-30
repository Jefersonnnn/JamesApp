package com.jm.jamesapp.models.transaction.enums;

public enum TransactionType {
    PAYMENT_RECEIVED(false),
    BILL_GROUP_PAID(true);

    private final Boolean isNegative;

    TransactionType(Boolean isNegative) {
        this.isNegative = isNegative;
    }

    public Boolean getIsNegative() {
        return isNegative;
    }
}
