package com.example.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 对象
     * @return JSON 字符串
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将对象转换为格式化的 JSON 字符串
     *
     * @param obj 对象
     * @return 格式化的 JSON 字符串
     */
    public static String toPrettyJson(Object obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
    }

    /**
     * 将 JSON 字符串转换为对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标对象类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将 JSON 字符串转换为 Map
     *
     * @param json JSON 字符串
     * @return Map 对象
     */
    public static Map<String, Object> toMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 将 JSON 字符串转换为 List
     *
     * @param json JSON 字符串
     * @return List 对象
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 将 JSON 字符串转换为 JSONObject
     *
     * @param json JSON 字符串
     * @return JSONObject 对象
     */
    public static JSONObject toJsonObject(String json) {
        return JSON.parseObject(json);
    }

    public static void main(String[] args) {
        // 示例对象
        User user = new User("John", 30);

        // 对象转 JSON 字符串
        String json = toJson(user);
        System.out.println("JSON: " + json);

        // 对象转格式化的 JSON 字符串
        String prettyJson = toPrettyJson(user);
        System.out.println("Pretty JSON: " + prettyJson);

        // JSON 字符串转对象
        User userFromJson = fromJson(json, User.class);
        System.out.println("User from JSON: " + userFromJson);

        // JSON 字符串转 Map
        Map<String, Object> map = toMap(json);
        System.out.println("Map: " + map);

        // JSON 字符串转 List
        String jsonArray = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Alice\",\"age\":25}]";
        List<User> userList = toList(jsonArray, User.class);
        System.out.println("User List: " + userList);

        // JSON 字符串转 JSONObject
        JSONObject jsonObject = toJsonObject(json);
        System.out.println("JSONObject: " + jsonObject);
    }

    // 示例类
    static class User {
        private String name;
        private int age;

        public User() {}

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
