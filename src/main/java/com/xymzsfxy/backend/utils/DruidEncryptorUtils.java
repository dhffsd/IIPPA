//package com.xymzsfxy.backend.utils;
//
//import com.alibaba.druid.filter.config.ConfigTools;
//import lombok.SneakyThrows;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//
//public final class DruidEncryptorUtils {
//
//    private static String privateKey;
//    private static String publicKey;
//
//    static {
//        try {
//            // 生成公私钥对
//            String[] keyPair = ConfigTools.genKeyPair(512);
//            privateKey = keyPair[0]; // 私钥
//            publicKey = keyPair[1];  // 公钥
//            System.out.println("私钥 (privateKey): " + privateKey);
//            System.out.println("公钥 (publicKey): " + publicKey);
//        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 使用私钥加密明文
//     *
//     * @param plaintext 明文
//     * @return 加密后的密文
//     */
//    @SneakyThrows
//    public static String encrypt(String plaintext) {
//        System.out.println("明文字符串：" + plaintext);
//        String ciphertext = ConfigTools.encrypt(privateKey, plaintext);
//        System.out.println("加密后字符串：" + ciphertext);
//        return ciphertext;
//    }
//
//    /**
//     * 使用公钥解密密文
//     *
//     * @param ciphertext 密文
//     * @return 解密后的明文
//     */
//    @SneakyThrows
//    public static String decrypt(String ciphertext) {
//        System.out.println("加密字符串：" + ciphertext);
//        String plaintext = ConfigTools.decrypt(publicKey, ciphertext);
//        System.out.println("解密后的字符串：" + plaintext);
//        return plaintext;
//    }
//
//    public static void main(String[] args) {
//        String plaintext = "myDatabasePassword"; // 要加密的明文
//        String encrypted = encrypt(plaintext);   // 加密
//        System.out.println("加密后的值: " + encrypted);
//
//        String decrypted = decrypt(encrypted);   // 解密
//        System.out.println("解密后的值: " + decrypted);
//    }
//}