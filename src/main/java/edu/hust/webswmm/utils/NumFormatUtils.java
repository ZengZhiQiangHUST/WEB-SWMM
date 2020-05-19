package edu.hust.webswmm.utils;

import java.math.BigDecimal;

public class NumFormatUtils
{
    public static String getPrettyNumber(String number)
    {
        return BigDecimal.valueOf(Double.parseDouble(number)).setScale(3,BigDecimal.ROUND_HALF_UP)
                         .stripTrailingZeros()
                         .toPlainString();
    }

    public static String getPrettyNumber(double number)
    {
        return BigDecimal.valueOf(number).setScale(3,BigDecimal.ROUND_HALF_UP)
                         .stripTrailingZeros()
                         .toPlainString();
    }
    public static String getPrettyNumber(float number)
    {
        return BigDecimal.valueOf(number).setScale(3,BigDecimal.ROUND_HALF_UP)
                         .stripTrailingZeros()
                         .toPlainString();
    }

    public static void main(String[] args)
    {
        System.out.println(getPrettyNumber(1.000));
    }

}
