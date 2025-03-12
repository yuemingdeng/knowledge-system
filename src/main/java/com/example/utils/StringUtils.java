package com.example.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断字符串是否为空或空白
     *
     * @param str 字符串
     * @return 是否为空或空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空或空白
     *
     * @param str 字符串
     * @return 是否不为空或空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 去除字符串首尾空白字符
     *
     * @param str 字符串
     * @return 去除空白后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 字符串
     * @return 是否为数字
     */
    public static boolean isNumeric(String str) {
        if (isBlank(str)) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    /**
     * 判断字符串是否为邮箱格式
     *
     * @param str 字符串
     * @return 是否为邮箱格式
     */
    public static boolean isEmail(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(regex, str);
    }

    /**
     * 判断字符串是否为手机号格式
     *
     * @param str 字符串
     * @return 是否为手机号格式
     */
    public static boolean isPhone(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.matches(regex, str);
    }

    /**
     * 字符串截取
     *
     * @param str   字符串
     * @param start 起始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的字符串
     */
    public static String substring(String str, int start, int end) {
        if (isBlank(str)) {
            return str;
        }
        if (start < 0 || end > str.length() || start > end) {
            throw new IllegalArgumentException("Invalid start or end position");
        }
        return str.substring(start, end);
    }

    /**
     * 字符串反转
     *
     * @param str 字符串
     * @return 反转后的字符串
     */
    public static String reverse(String str) {
        if (isBlank(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 字符串格式化
     *
     * @param format 格式化模板
     * @param args   参数
     * @return 格式化后的字符串
     */
    public static String format(String format, Object... args) {
        if (isBlank(format)) {
            return format;
        }
        return String.format(format, args);
    }

    /**
     * 字符串拼接
     *
     * @param delimiter 分隔符
     * @param parts     字符串数组
     * @return 拼接后的字符串
     */
    public static String join(String delimiter, String... parts) {
        if (parts == null || parts.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);
            if (i < parts.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 测试 isBlank 和 isNotBlank
        System.out.println("Is blank: " + isBlank("   ")); // true
        System.out.println("Is not blank: " + isNotBlank("Hello")); // true

        // 测试 trim
        System.out.println("Trim: '" + trim("  Hello World  ") + "'"); // 'Hello World'

        // 测试 randomString
        System.out.println("Random string: " + randomString(10));

        // 测试 isNumeric
        System.out.println("Is numeric: " + isNumeric("12345")); // true
        System.out.println("Is numeric: " + isNumeric("123a45")); // false

        // 测试 isEmail
        System.out.println("Is email: " + isEmail("test@example.com")); // true
        System.out.println("Is email: " + isEmail("invalid-email")); // false

        // 测试 isPhone
        System.out.println("Is phone: " + isPhone("13800138000")); // true
        System.out.println("Is phone: " + isPhone("12345678901")); // false

        // 测试 substring
        System.out.println("Substring: " + substring("Hello World", 0, 5)); // Hello

        // 测试 reverse
        System.out.println("Reverse: " + reverse("Hello")); // olleH

        // 测试 format
        System.out.println("Format: " + format("Hello, %s!", "World")); // Hello, World!

        // 测试 join
        System.out.println("Join: " + join(", ", "A", "B", "C")); // A, B, C
    }
}
