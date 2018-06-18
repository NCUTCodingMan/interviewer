package com.rain.flowers;

import java.math.BigDecimal;

/**
 * @author sourc
 * @date 2018/6/11
 */
public class Caculator {
    public static void main(String[] args) {
        BigDecimal init = new BigDecimal("100000.00");

        BigDecimal rate = new BigDecimal("2.80");

        BigDecimal day = new BigDecimal("31.00");

        BigDecimal perMonth = new BigDecimal("2778.67");

        BigDecimal sum = new BigDecimal("0.00");

        int last = 36;

        for (int i = 0; i < last; i++) {
            sum = sum.add(perMonth);
            BigDecimal iterator = init.divide(new BigDecimal("10000")).multiply(day).multiply(rate);
            sum = sum.add(iterator);
            init = init.subtract(perMonth);
            System.out.println(init + "\t" + iterator);
        }

        System.out.print(sum);
    }
}
