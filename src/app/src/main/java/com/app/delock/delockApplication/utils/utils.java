package com.app.delock.delockApplication.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Marky on 30/04/2018.
 */

public class utils {
    static double round(double value, int precision) {
        if (precision < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(precision, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
