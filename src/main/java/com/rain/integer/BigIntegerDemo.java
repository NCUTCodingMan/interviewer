package com.rain.integer;

import java.math.BigInteger;

/**
 * 如何使用BigInteger做位运算,构造函数中的String用来初始化二进制
 * setBit(n) 设置某一位为1
 *  this | (1 << n)
 * testBit(n) 判断某一位是否为1,当相与的结果为1时;返回true,否则返回false
 *  this & (1 << n)
 * */
public class BigIntegerDemo {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("127");
        System.out.println(bigInteger.toString());
        
        // 计算 this | (1 << n)
        bigInteger = bigInteger.setBit(7);
        System.out.println(bigInteger.intValue());
        
        bigInteger = bigInteger.setBit(2);
        System.out.println(bigInteger.intValue());
        
        System.out.println(bigInteger.testBit(7));
    }
}
