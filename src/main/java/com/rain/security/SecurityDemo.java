package com.rain.security;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;

/**
 * RSA非对称加密
 *  发送方:使用公钥加密原文, 传输给接收方
 *  接收方:使用秘钥解密原文, 查看原文信息
 * RSA存在的一个问题是, 不能确定公钥是否是真的. 也就是说, 可以存在第三者,
 * 第三者将自己的公钥发给发送方(A), A以为是接收方(B)的公钥, 因此, 采用
 * 第三者的公钥加密, 第三者收到加密后的原文后使用自己的秘钥解密. 篡改信息
 * 并使用B的公钥加密, 至此, A与B之间的通信完全可以被第三者看到
 *
 * 在此基础之上, 演化出了数字签名
 *
 * @author rain
 * created on 2018/4/8
 */
public class SecurityDemo {
    private static Key PUBLIC_KEY = null;
    private static Key PRIVATE_KEY = null;
    private static final String ALGORITHM = "RSA";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        decrypt(encrypt(scanner.nextLine()));
        scanner.close();

    }

    static {
        KeyPairGenerator keyPairGenerator;
        try {
            // 设置加密算法
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            // 获取公私钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 初始化公私钥
            PUBLIC_KEY = keyPair.getPublic();
            PRIVATE_KEY = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] encrypt(String content) throws Exception {
        // 公钥加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);

        // 加密传输
        return cipher.doFinal(content.getBytes());
    }

    private static void decrypt(byte[] content) throws Exception {
        // 私钥解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, PRIVATE_KEY);

        // 解密为原文
        byte[] result = cipher.doFinal(content);
        // 将原文解析为字符串
        System.out.println(new java.lang.String(result, "utf-8"));
    }
}
