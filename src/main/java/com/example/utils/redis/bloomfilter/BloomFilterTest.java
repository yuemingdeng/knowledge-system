package com.example.utils.redis.bloomfilter;

import java.util.ArrayList;
import java.util.List;

public class BloomFilterTest {

    public static void main(String[] args) {
        BloomFilter bloomFilter = BloomFilterSingleton.getInstance();

        // 添加单个元素
        bloomFilter.add("apple");
        bloomFilter.add("banana");

        // 批量添加元素
        List<String> fruits = new ArrayList<>();
        fruits.add("orange");
        fruits.add("grape");
        fruits.add("melon");
        bloomFilter.addAll(fruits);

        // 检查元素是否存在
        System.out.println("Contains 'apple': " + bloomFilter.contains("apple"));   // true
        System.out.println("Contains 'grape': " + bloomFilter.contains("grape")); // true
        System.out.println("Contains 'pear': " + bloomFilter.contains("pear"));   // false (可能误判)

        // 删除布隆过滤器
        bloomFilter.delete();
        System.out.println("Contains 'apple' after deletion: " + bloomFilter.contains("apple")); // false

        //关闭连接
        bloomFilter.getJedis().close();
    }
}
