package com.example.utils.distributedidicreate;

import java.util.UUID;

public class CreateIdByUUID {
    public static void main(String[] args) {
        // 生成随机 UUID
        UUID uuid = UUID.randomUUID();
        System.out.println("随机 UUID: " + uuid); // 输出示例: 1a2b-3c4d-5e6f-7a8b-9c0d1e2f3a4b

        // 获取特定字段（如时间戳）
        long timestamp = uuid.getMostSignificantBits(); // 版本1的时间戳（部分实现可能不精确）
        System.out.println("时间戳: " + timestamp);

        // 转换为字符串格式（不带横杠）
        String uuidStr = uuid.toString().replace("-", "");
        System.out.println("无横杠 UUID: " + uuidStr);
    }
}
