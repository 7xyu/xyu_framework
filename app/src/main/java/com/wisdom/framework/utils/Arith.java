package com.wisdom.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author xyu
 * @describe 单精度双精度计算类
 * @date 2015-3-2
 */
public class Arith {

    /**
     * 转化为正规计数数字
     *
     * @param number
     * @return
     */
    public static String format(BigDecimal number) {
        if (number == null)
            return "";
        if (number.compareTo(BigDecimal.ZERO) == 0) return "0";
        return new DecimalFormat(",###.##").format(number
                .stripTrailingZeros());
    }

    /**
     * 转化为正规计数数字
     *
     * @param number
     * @return
     */
    public static String formatNormalNumber(double number) {
        if (number == 0) return "0";
        return new DecimalFormat("###.##").format(number);
    }

    /**
     * 转化金额
     *
     * @param number
     * @return
     */
    public static String fMoney(Object number) {
        if (number == null) return "";
        return new DecimalFormat(",##0.00").format(number);
    }


    /**
     * 转化为普通数字
     * @param number
     * @return
     */
    public static String f(BigDecimal number, int scale) {
        if (number == null)
            return "";
        if (number.compareTo(BigDecimal.ZERO) == 0) return "0";
        return number.setScale(scale, BigDecimal.ROUND_HALF_UP)
                .stripTrailingZeros().toPlainString();
    }

    /**
     * 转化为普通数字,保留2位
     *
     * @param number
     * @return
     */
    public static String f2(BigDecimal number) {
        return f(number, 2);
    }


    /**
     * 转化为普通数字,不保留取整
     *
     * @param number
     * @return
     */
    public static String f0(BigDecimal number) {
        return f(number, 0);
    }

    /**
     * 转化为普通数字,保留5位
     *
     * @param number
     * @return
     */
    public static String fForNum(BigDecimal number) {
        return f(number, 4);
    }

    /**
     * 转化为普通数字,保留5位
     *
     * @param number
     * @return
     */
    public static String f5(BigDecimal number) {
        return f(number, 5);
    }

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(value1));
        BigDecimal b2 = new BigDecimal(String.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale)
            throws IllegalAccessException {
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal div(BigDecimal value1, BigDecimal value2, int scale) {
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new RuntimeException("精确度不能小于0");
        }
        return value1.divide(value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 处理float类型数据相乘丢失精度 用于再次运算,或者转化为字符串
     *
     * @param f1
     * @param f2
     * @return
     */
    public static BigDecimal mul(float f1, float f2) {
        BigDecimal bd1 = new BigDecimal(String.valueOf(f1));
        BigDecimal bd2 = new BigDecimal(String.valueOf(f2));
        return bd1.multiply(bd2);
    }

    public static boolean isNumOk(BigDecimal b) {
        return b != null && b.compareTo(BigDecimal.ZERO) != 0;
    }

}