package com.jm.jamesapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static Boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static Boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static BigDecimal divideValueMonetary(BigDecimal value, BigDecimal divisor) {
        return value.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
