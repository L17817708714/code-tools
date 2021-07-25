package com.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @className: RegCodeGenerateUtil
 * @description: 注册码生成工具类
 * @date 2021/7/25 15:08
 * @version：1.0
 */
public class RegCodeGenerateUtil {

    // NUM值的位数，生成的注册码是 2 * BIT_NUM 位。
    private static final int BIT_NUM = 8;
    // NUM值不大于MAX_NUM
    private static final int MAX_NUM = (int) Math.pow(10, BIT_NUM) - 1;

    private static final String STRING_FOMAT = "%0" + BIT_NUM + "d";

    private static final AtomicInteger NUM = new AtomicInteger(0);
    // 雪花算法生成的码作为私钥
    private static final SnowFlake SNOWFLAKE  = new SnowFlake(0,0);

    /**
     * @Description: 字符串转换位 16 进制方法.
     * ASCII 码的范围在 最大在 0 - 255
     * 转换16进制范围在 00-ff
     * 每个数值得到2位 16进制数。
     * @param s:
     * @return java.lang.String
     * @author Administrator
     * @date 2021/7/25 17:37
     */
    public static String strTo16(String s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s1 = Integer.toHexString(ch);
            if (s1.length() == 1) {
                // 补 0
                s1 = "0" + s1;
            }
            str.append(s1);
        }
        return str.toString();
    }

    /**
     * @Description: 生成注册码方法
     * 通过 RC4 算法加密得到密文，密文转换位16进制输出
     * num自增，私钥为雪花算法生成，每次都不重复
     * @return java.lang.String
     * @author Administrator
     * @date 2021/7/25 17:41
     */
    public static String generate() {
        int num = NUM.getAndIncrement();
        if (num == MAX_NUM) {
            synchronized (RegCodeGenerateUtil.class) {
                if (NUM.get() != 0) {
                    // 自增到最大，又从 0 开始，因为私钥也一直在改变，所以不会出现重复
                    NUM.set(0);
                }
            }
        }
        // RC4 算法加密，key 为 num，私钥为雪花算法生成
        String res = RC4.HloveyRC4(String.format(STRING_FOMAT, num), String.valueOf(SNOWFLAKE.nextId()));
        return strTo16(res).toUpperCase();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(generate());
        }
    }
}
