package com.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 功能说明
 * 需要获取当前日期和时间的场景。
 * 需要格式化或解析日期和时间的场景。
 * 需要对日期进行加减操作的场景。
 * 需要计算日期差或判断日期范围的场景。
 * 需要处理时间戳的场景。
 */
public class DateTimeUtils {

    // 默认日期格式
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    // 默认日期时间格式
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期
     *
     * @return 当前日期（LocalDate）
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间（LocalTime）
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期和时间
     *
     * @return 当前日期和时间（LocalDateTime）
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 格式化日期
     *
     * @param date   日期
     * @param format 日期格式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    /**
     * 格式化日期时间
     *
     * @param dateTime 日期时间
     * @param format   日期时间格式
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    /**
     * 解析日期字符串
     *
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return 解析后的日期（LocalDate）
     */
    public static LocalDate parseDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 解析日期时间字符串
     *
     * @param dateTimeStr 日期时间字符串
     * @param format      日期时间格式
     * @return 解析后的日期时间（LocalDateTime）
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 日期加减操作
     *
     * @param date   日期
     * @param amount 数量
     * @param unit   时间单位（ChronoUnit.DAYS, ChronoUnit.MONTHS 等）
     * @return 加减后的日期
     */
    public static LocalDate addDate(LocalDate date, long amount, ChronoUnit unit) {
        return date.plus(amount, unit);
    }

    /**
     * 日期时间加减操作
     *
     * @param dateTime 日期时间
     * @param amount   数量
     * @param unit     时间单位（ChronoUnit.HOURS, ChronoUnit.MINUTES 等）
     * @return 加减后的日期时间
     */
    public static LocalDateTime addDateTime(LocalDateTime dateTime, long amount, ChronoUnit unit) {
        return dateTime.plus(amount, unit);
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数差
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 判断日期是否在范围内
     *
     * @param date  日期
     * @param start 开始日期
     * @param end   结束日期
     * @return 是否在范围内
     */
    public static boolean isDateInRange(LocalDate date, LocalDate start, LocalDate end) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * 获取某个月的第一天
     *
     * @param date 日期
     * @return 该月的第一天
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取某个月的最后一天
     *
     * @param date 日期
     * @return 该月的最后一天
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 将日期转换为时间戳（毫秒）
     *
     * @param date 日期
     * @return 时间戳（毫秒）
     */
    public static long toTimestamp(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将时间戳转换为日期时间
     *
     * @param timestamp 时间戳（毫秒）
     * @return 日期时间
     */
    public static LocalDateTime fromTimestamp(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static void main(String[] args) {
        // 获取当前日期和时间
        LocalDate currentDate = getCurrentDate();
        LocalDateTime currentDateTime = getCurrentDateTime();
        System.out.println("Current Date: " + currentDate);
        System.out.println("Current DateTime: " + currentDateTime);

        // 格式化日期
        String formattedDate = formatDate(currentDate, DEFAULT_DATE_FORMAT);
        System.out.println("Formatted Date: " + formattedDate);

        // 格式化日期时间
        String formattedDateTime = formatDateTime(currentDateTime, DEFAULT_DATETIME_FORMAT);
        System.out.println("Formatted DateTime: " + formattedDateTime);

        // 解析日期字符串
        LocalDate parsedDate = parseDate("2023-10-01", DEFAULT_DATE_FORMAT);
        System.out.println("Parsed Date: " + parsedDate);

        // 解析日期时间字符串
        LocalDateTime parsedDateTime = parseDateTime("2023-10-01 12:34:56", DEFAULT_DATETIME_FORMAT);
        System.out.println("Parsed DateTime: " + parsedDateTime);

        // 日期加减操作
        LocalDate newDate = addDate(currentDate, 10, ChronoUnit.DAYS);
        System.out.println("Date after adding 10 days: " + newDate);

        // 日期时间加减操作
        LocalDateTime newDateTime = addDateTime(currentDateTime, 2, ChronoUnit.HOURS);
        System.out.println("DateTime after adding 2 hours: " + newDateTime);

        // 计算日期差
        long daysBetween = daysBetween(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 10));
        System.out.println("Days between 2023-10-01 and 2023-10-10: " + daysBetween);

        // 判断日期是否在范围内
        boolean isInRange = isDateInRange(LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 10));
        System.out.println("Is 2023-10-05 in range: " + isInRange);

        // 获取某个月的第一天和最后一天
        LocalDate firstDayOfMonth = getFirstDayOfMonth(currentDate);
        LocalDate lastDayOfMonth = getLastDayOfMonth(currentDate);
        System.out.println("First day of month: " + firstDayOfMonth);
        System.out.println("Last day of month: " + lastDayOfMonth);

        // 日期与时间戳的转换
        long timestamp = toTimestamp(currentDateTime);
        System.out.println("Timestamp: " + timestamp);
        LocalDateTime dateTimeFromTimestamp = fromTimestamp(timestamp);
        System.out.println("DateTime from timestamp: " + dateTimeFromTimestamp);
    }
}