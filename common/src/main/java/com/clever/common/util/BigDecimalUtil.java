package com.clever.common.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    // 加法 (向下舍入)
    public static <T extends Number> int compareTo(T v1, T v2) {
        return new BigDecimal(v1.toString()).compareTo(new BigDecimal(v2.toString()));
    }

    // 加法 (向下舍入)
    public static <T extends Number> Double add(T v1, T v2, int scale) {
        return roundDown(new BigDecimal(v1.toString()).add(new BigDecimal(v2.toString())), scale);
    }

    // 加法 (向上舍入)
    public static <T extends Number> Double addUp(T v1, T v2, int scale) {
        return roundUp(new BigDecimal(v1.toString()).add(new BigDecimal(v2.toString())), scale);
    }

    // 减法 (向下舍入)
    public static <T extends Number> Double sub(T v1, T v2, int scale) {
        return roundDown(new BigDecimal(v1.toString()).subtract(new BigDecimal(v2.toString())), scale);
    }

    // 乘法(向下舍入)
    public static <T extends Number> Double mul(T v1, T v2, int scale) {
        return roundDown(new BigDecimal(v1.toString()).multiply(new BigDecimal(v2.toString())), scale);
    }

    // 乘法(向上舍入)
    public static <T extends Number> Double mulUp(T v1, T v2, int scale) {
        return roundUp(new BigDecimal(v1.toString()).multiply(new BigDecimal(v2.toString())), scale);
    }

    // 除法 (向下舍入)
    public static <T extends Number> Double div(T v1, T v2, int scale) {
        if (v2.equals(0)) {
            return 0D;
        }
        return new BigDecimal(v1.toString()).divide(new BigDecimal(v2.toString()), scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    // 采用向下舍入的方式
    public static Double roundDown(BigDecimal num, int scale) {
        return num.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    // 除法 (向下舍入)
    public static <T extends Number> String divToString(T v1, T v2, int scale) {
        if (v2.equals(0)) {
            return "0";
        }
        String str = new BigDecimal(v1.toString()).divide(new BigDecimal(v2.toString()), scale, BigDecimal.ROUND_DOWN).toPlainString();
        return str;
    }

    public static void main(String[] args) {
        String price = "21474836.47";
        Double d = Double.parseDouble(Integer.MAX_VALUE + "");
        System.out.println(BigDecimalUtil.mul(d, 100, 0).longValue());
        System.out.println(Float.parseFloat(d.toString()));
        System.out.println(BigDecimalUtil.add(1, 0.001, 0));
        System.out.println(BigDecimalUtil.divToString(Integer.MAX_VALUE, 100, 2));
    }

    // 采用向上舍入的方式
    public static Double roundUp(BigDecimal num, int scale) {
        return num.setScale(scale, BigDecimal.ROUND_UP).doubleValue();
    }

    public static String subtractToStr(BigDecimal sourceBigDecimal, int num) {
        return sourceBigDecimal.subtract(new BigDecimal(num)).toString();
    }
}
