package com.utils;

import java.util.Random;

/**
 * @author Administrator
 * @className: RC4
 * @description: RC4算法
 * @date 2021/7/25 11:12
 * @version：1.0
 */
public class RC4 {

    public static String HloveyRC4(String aInput, String aKey) {
        int[] iS = new int[256];
        byte[] iK = new byte[256];

        // 1.1 KSA--密钥调度算法--利用key来对S盒做一个置换，也就是对S盒重新排列
        for (int i = 0; i < 256; i++)
            iS[i] = i;

        int j;

        for (short i = 0; i < 256; i++) {
            iK[i] = (byte) aKey.charAt((i % aKey.length()));
        }

        j = 0;

        for (int i = 0; i < 256; i++) {
            j = (j + iS[i] + iK[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
        }

        // 1.2 RPGA--伪随机生成算法--利用上面重新排列的S盒来产生任意长度的密钥流
        int i = 0;
        j = 0;
        char[] iInputChar = aInput.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];
        for (short x = 0; x < iInputChar.length; x++) {
            i = (i + 1) % 256;
            j = (j + iS[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
            int t = (iS[i] + (iS[j] % 256)) % 256;
            int iY = iS[t];
            char iCY = (char) iY;
            iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
        }
        return new String(iOutputChar);
    }

    // 随机秘钥方法
    public static String getKey() {
        char[] k = new char[16];
        Random r = new Random();
        for (int i = 0; i < 16; i++) {
            k[i] = (char) ('a' + r.nextInt() % 26);
        }
        return new String(k);

    }

    public static void main(String[] args) {
        String inputStr = "asdadssadas";
//        String key = "abcdefghijklmnop";   // 固定秘钥

        String key = getKey();              // 随机秘钥
//        System.out.println("秘钥是：" + key);

        String str = HloveyRC4(inputStr, key);

        //打印加密后的字符串
        System.out.println(str);

        //打印解密后的字符串
        System.out.println(HloveyRC4(str, key));
    }
}
