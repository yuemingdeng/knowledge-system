package com.example.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 功能说明
 * MD5加密：
 *  使用 MessageDigest 实现 MD5 加密。
 *  返回 32 位小写的十六进制字符串。
 *
 * SHA-256加密：
 *  使用 MessageDigest 实现 SHA-256 加密。
 *  返回 64 位小写的十六进制字符串。
 *
 * AES加密与解密：
 *    使用 Cipher 实现 AES 对称加密与解密。
 *    支持 128 位密钥长度。
 *    密钥和加密结果均为 Base64 编码。
 *
 * RSA加密与解密：
 *  使用 Cipher 实现 RSA 非对称加密与解密。
 *  支持 2048 位密钥长度。
 *  公钥和私钥均为 Base64 编码。
 *
 * Base64编码与解码：
 *  使用 Base64 类实现字符串的编码与解码。
 */
public class CryptoUtils {

    // AES 密钥长度
    private static final int AES_KEY_SIZE = 128;

    // RSA 密钥长度
    private static final int RSA_KEY_SIZE = 2048;

    /**
     * MD5 加密
     *
     * @param input 输入字符串
     * @return MD5 加密后的字符串（32位小写）
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * SHA-256 加密
     *
     * @param input 输入字符串
     * @return SHA-256 加密后的字符串（64位小写）
     */
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * AES 加密
     *
     * @param input 输入字符串
     * @param key   AES 密钥（Base64 编码）
     * @return AES 加密后的字符串（Base64 编码）
     */
    public static String aesEncrypt(String input, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    /**
     * AES 解密
     *
     * @param input 输入字符串（Base64 编码）
     * @param key   AES 密钥（Base64 编码）
     * @return AES 解密后的字符串
     */
    public static String aesDecrypt(String input, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }

    /**
     * 生成 AES 密钥
     *
     * @return AES 密钥（Base64 编码）
     */
    public static String generateAesKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(AES_KEY_SIZE);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES key generation failed", e);
        }
    }

    /**
     * RSA 加密
     *
     * @param input 输入字符串
     * @param key   RSA 公钥（Base64 编码）
     * @return RSA 加密后的字符串（Base64 编码）
     */
    public static String rsaEncrypt(String input, String key) {
        try {
            PublicKey publicKey = getPublicKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("RSA encryption failed", e);
        }
    }

    /**
     * RSA 解密
     *
     * @param input 输入字符串（Base64 编码）
     * @param key   RSA 私钥（Base64 编码）
     * @return RSA 解密后的字符串
     */
    public static String rsaDecrypt(String input, String key) {
        try {
            PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("RSA decryption failed", e);
        }
    }

    /**
     * 生成 RSA 密钥对
     *
     * @return RSA 密钥对（公钥和私钥，Base64 编码）
     */
    public static KeyPair generateRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(RSA_KEY_SIZE);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA key pair generation failed", e);
        }
    }

    /**
     * Base64 编码
     *
     * @param input 输入字符串
     * @return Base64 编码后的字符串
     */
    public static String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64 解码
     *
     * @param input 输入字符串（Base64 编码）
     * @return Base64 解码后的字符串
     */
    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 从 Base64 编码的字符串中获取 RSA 公钥
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从 Base64 编码的字符串中获取 RSA 私钥
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) {
        // MD5 加密
        System.out.println("MD5: " + md5("Hello, World!"));

        // SHA-256 加密
        System.out.println("SHA-256: " + sha256("Hello, World!"));

        // AES 加密与解密
        String aesKey = generateAesKey();
        String aesEncrypted = aesEncrypt("Hello, World!", aesKey);
        System.out.println("AES Encrypted: " + aesEncrypted);
        System.out.println("AES Decrypted: " + aesDecrypt(aesEncrypted, aesKey));

        // RSA 加密与解密
        KeyPair rsaKeyPair = generateRsaKeyPair();
        String rsaPublicKey = Base64.getEncoder().encodeToString(rsaKeyPair.getPublic().getEncoded());
        String rsaPrivateKey = Base64.getEncoder().encodeToString(rsaKeyPair.getPrivate().getEncoded());
        String rsaEncrypted = rsaEncrypt("Hello, World!", rsaPublicKey);
        System.out.println("RSA Encrypted: " + rsaEncrypted);
        System.out.println("RSA Decrypted: " + rsaDecrypt(rsaEncrypted, rsaPrivateKey));

        // Base64 编码与解码
        String base64Encoded = base64Encode("Hello, World!");
        System.out.println("Base64 Encoded: " + base64Encoded);
        System.out.println("Base64 Decoded: " + base64Decode(base64Encoded));
    }
}