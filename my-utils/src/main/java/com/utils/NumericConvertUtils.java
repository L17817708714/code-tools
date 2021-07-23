package com.utils;

/**
 * @author Administrator
 * @className: NumericConvertUtils
 * @description: 进制转换工具
 * 将其他数字转换为进制数，最大支持62进制的转换
 * @date 2021/7/23 10:59
 * @version：1.0
 */
public class NumericConvertUtils {

    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * @Description: 10 进制数转换为 seed 进制数
     * @param number: 10进制数
     * @param seed: 要转成的 seed 进制
     * @return java.lang.String
     * @author Administrator
     * @date 2021/7/23 12:42
     */
    public static String toOtherNumberSystem(long number, int seed) {
        if (number == 0) {
            return "0";
        }
        char[] chars = new char[64];
        int index = 0;
        while (number > 0) {
            int remainder = (int) (number % seed);
            chars[chars.length - 1 - index] = DIGITS[remainder];
            number = number / seed;
            index++;
        }
        return new String(chars, chars.length - index, index);
    }
    /**
     * @Description: seed 进制数转换为 10 进制数
     * @param number: 进制数
     * @param seed: seed 进制
     * @return long
     * @author Administrator
     * @date 2021/7/23 12:57
     */
    public static long toDecimalNumber(String number, int seed) {
        char[] chars = number.toCharArray();
        long sum = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i == chars.length - 1) {
                sum += getIndex(chars[i]);
            }else {
                sum = (sum + getIndex(chars[i])) * seed;
            }
        }
        return sum;
    }

    private static int getIndex(char a) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == a) {
                return i;
            }
        }
        return -1;
    }

}
