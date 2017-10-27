package com.rain.string;

/**
 * String, StringBuilder, StringBuffer
 * 1.三者都是final类型,不能被继承
 * 2.StringBuffer线程安全,String,StringBuilder线程不安全
 * 3.String不可变,StringBuilder,StringBuffer可变
 */
public class StringDemo {
    public static void main(String args) {
        // String字符串之间的相加可解析为new StringBuilder(s1).append(s2)
        String s1 = "he";
        String s2 = "h2";
        s1 = s1 + s2;

        // 对于大量的字符串拼接操作,尽量设置StringBuilder的缓存区(初始值设置大一些,底层使用char[]实现,可能会出现数据拷贝的操作)
    }
}
