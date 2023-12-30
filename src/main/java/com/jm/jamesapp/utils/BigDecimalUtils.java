package com.jm.jamesapp.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static Boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static Boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }
}
