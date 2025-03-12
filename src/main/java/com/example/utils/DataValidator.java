package com.example.utils;

import java.util.regex.Pattern;

/**
 * 功能说明
 * 邮箱校验：
 *  使用正则表达式验证邮箱格式。
 *  支持常见的邮箱格式，如 test@example.com。
 *
 * 手机号校验：
 *  使用正则表达式验证11位手机号。
 *  支持以 1 开头，第二位为 3-9 的手机号。
 *
 * 身份证校验：
 *  使用正则表达式验证15位或18位身份证号。
 *  支持18位身份证号的最后一位为 X 或 x。
 *
 * URL校验：
 *  使用正则表达式验证URL格式。
 *  支持 http、https、ftp 等协议。
 *
 * 正则表达式校验：
 *  支持自定义正则表达式校验。
 *  可以用于校验任意格式的字符串。
 */
public class DataValidator {

    /**
     * 邮箱校验
     *
     * @param email 邮箱地址
     * @return 是否为合法的邮箱地址
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // 邮箱正则表达式
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(regex, email);
    }

    /**
     * 手机号校验
     *
     * @param phone 手机号
     * @return 是否为合法的手机号
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        // 手机号正则表达式（支持11位手机号）
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 身份证校验
     *
     * @param idCard 身份证号
     * @return 是否为合法的身份证号
     */
    public static boolean isValidIdCard(String idCard) {
        if (idCard == null || idCard.isEmpty()) {
            return false;
        }
        // 身份证正则表达式（支持15位和18位身份证号）
        String regex = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";
        return Pattern.matches(regex, idCard);
    }

    /**
     * URL校验
     *
     * @param url URL地址
     * @return 是否为合法的URL
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        // URL正则表达式
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return Pattern.matches(regex, url);
    }

    /**
     * 正则表达式校验
     *
     * @param input 输入字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean isValidByRegex(String input, String regex) {
        if (input == null || regex == null || input.isEmpty() || regex.isEmpty()) {
            return false;
        }
        return Pattern.matches(regex, input);
    }

    public static void main(String[] args) {
        // 测试邮箱校验
        System.out.println("Email validation:");
        System.out.println(isValidEmail("test@example.com")); // true
        System.out.println(isValidEmail("invalid-email"));   // false

        // 测试手机号校验
        System.out.println("Phone validation:");
        System.out.println(isValidPhone("13800138000")); // true
        System.out.println(isValidPhone("12345678901")); // false

        // 测试身份证校验
        System.out.println("ID Card validation:");
        System.out.println(isValidIdCard("110105199003071234")); // true
        System.out.println(isValidIdCard("123456789012345"));    // true (15位)
        System.out.println(isValidIdCard("12345678901234567X")); // true (18位，最后一位是X)
        System.out.println(isValidIdCard("1234567890123456780")); // false

        // 测试URL校验
        System.out.println("URL validation:");
        System.out.println(isValidUrl("https://www.example.com")); // true
        System.out.println(isValidUrl("ftp://example.com"));       // true
        System.out.println(isValidUrl("invalid-url"));             // false

        // 测试自定义正则表达式校验
        System.out.println("Custom regex validation:");
        System.out.println(isValidByRegex("12345", "\\d{5}")); // true
        System.out.println(isValidByRegex("abcde", "\\d{5}")); // false
    }
}
