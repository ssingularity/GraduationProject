package com.sjtu.project.common.util;

import org.junit.Assert;

import java.util.UUID;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/7 19:43
 */
public class UUIDUtils {

    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 生成长度为length的UUID
     * @param length 生成字段的长度, 其中1<=length<=8
     * @return String
     */
    public static String generateUUID(int length) {
        Assert.assertTrue(1 <= length && length <= 8);
        //调用Java提供的生成随机字符串的对象：32位，十六进制，中间包含-
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuffer shortBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}
