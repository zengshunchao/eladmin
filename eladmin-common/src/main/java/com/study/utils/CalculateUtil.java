package com.study.utils;

import java.math.BigDecimal;

/**
 * 数值计算
 */
public class CalculateUtil {

    /**
     * 两个Double数相加
     *
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 两个Double数相减
     *
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个Double数相乘
     *
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }
}
