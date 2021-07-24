package com.utils;

import java.util.Arrays;

/**
 * @author Administrator
 * @className: BitMap
 * @description: Bitmap 工具类
 * 采用位图的方式，每个 byte 能存储8个数字，
 * 用于确定该数存在不存在
 * @date 2021/7/24 10:35
 * @version：1.0
 */
public class BitMap {

    private final byte[] bytes;
    // 最大容量
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    // 最小容量
    private static final int DIGIT_NUM = 8;

    /**
     * @param capacity:
     * @return
     * @Description: 初始化容量
     * @author Administrator
     * @date 2021/7/24 10:49
     */
    public BitMap(int capacity) {
        bytes = new byte[tableSizeFor(capacity) >>> 3];
    }

    /**
     * @Description: 目标容器为8的倍数，最小为8
     * @param cap:
     * @return int
     * @author Administrator
     * @date 2021/7/24 13:52
     */
    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n < DIGIT_NUM ? DIGIT_NUM : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    /**
     * @Description: 设置对应位的状态
     * @param num: 被设置的数值，不超过范围
     * @param bool: true,1;fale,0
     * @return void
     * @author Administrator
     * @date 2021/7/24 12:59
     */
    public void set(int num, boolean bool) throws IllegalAccessException {
        // 先获取下标
        int index = num >>> 3;
        byte node = getByIndex(index);
        // 获取在该节点的第几位
        int remainder = num & 7; // 相当于 num & 8
        if (bool) {
            // true 设置对应位为 1
            node = (byte) (node | (1 << remainder));
        }else {
            // false 设置对应位为 0
            node = (byte) (node & (~(1 << remainder)));
        }
        bytes[index] = node;
    }
    
    /**
     * @Description: 获取对应位的状态
     * @param num: 
     * @return boolean
     * @author Administrator
     * @date 2021/7/24 13:01
     */
    public boolean get(int num) throws IllegalAccessException {
        // 先获取下标
        int index = num >>> 3;
        byte node = getByIndex(index);
        // 获取在该节点的第几位
        int remainder = num & 7; // 相当于 num & 8
        return (node & (1 << remainder)) != 0;
    }

    private byte getByIndex(int index) throws IllegalAccessException {
        if (index >= bytes.length) {
            throw new IllegalAccessException("越界");
        }
        return bytes[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(bytes);
    }

}
