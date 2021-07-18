package com.utils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @className: FstUtil
 * @description: 字典树构建工具类
 * @date 2021/7/17 19:07
 * @version：1.0
 */
public class TrieUtil {

    private static final Map<Byte, Node> root = new HashMap<>();

    /**
     * @Description: 根据 key 获取 val
     * @param key:
     * @return java.lang.String
     * @author Administrator
     * @date 2021/7/17 21:26
     */
    public static String getVal(String key) {
        // key 值校验
        if (null == key || "".equals(key)) {
            return null;
        }
        char[] chars = key.toCharArray();
        byte headKey = (byte) chars[0];
        if (!root.containsKey(headKey)) {
            return null;
        }
        return getNodeVal(root.get(headKey), chars, 1);
    }

    /**
     * @Description: 获取非头节点val
     * @param node:
     * @param chars:
     * @param index:
     * @return java.lang.String
     * @author Administrator
     * @date 2021/7/17 21:28
     */
    private static String getNodeVal(Node node, char[] chars, int index) {
        byte key = (byte) chars[index];
        // 获取 下一元素指向
        Map<Byte, Node> next = node.getNext();
        if (!next.containsKey(key)) {
            return null;
        }
        Node node1 = next.get(key);
        if (index == chars.length - 1) {
            return Arrays.toString(node1.getVal());
        }
        return getNodeVal(node1, chars, ++index);
    }

    /**
     * @Description: 添加 key-val
     * @param key:
     * @param val:
     * @return void
     * @author Administrator
     * @date 2021/7/17 20:52
     */
    public static void addVal(String key, String val) {
        // key 值校验
        if (null == key || "".equals(key) || null == val) {
            return;
        }
        char[] chars = key.toCharArray();
        // 不存在，则添加头节点
        byte headKey = (byte) chars[0];
        if (!root.containsKey(headKey)) {
            Node head = new Node(headKey);
            root.put(headKey, head);
        }
        // 获取头节点
        Node headNode = root.get(headKey);
        if (chars.length > 1) {
            addNode(headNode, chars, 1, val);
        }else {
            headNode.setVal(val.getBytes());
        }
    }

    /**
     * @Description: 添加非头节点元素
     * @param node: 节点
     * @param chars: char[]数组
     * @param index: char[]数组下标
     * @param val: val值
     * @return void
     * @author Administrator
     * @date 2021/7/17 21:21
     */
    private static synchronized void addNode(Node node, char[] chars, int index, String val) {
        byte key = (byte) chars[index];
        // 获取 下一元素指向
        Map<Byte, Node> next = node.getNext();
        if (null == next) {
            next = new HashMap<>();
            node.setNext(next);
        }
        // 不存在，则添加节点
        if (!next.containsKey(key)) {
            Node node1 = new Node(key);
            next.put(key, node1);
        }
        Node node1 = next.get(key);
        if (index == chars.length - 1) {
            node1.setVal(val.getBytes());
        }else {
            addNode(node1, chars, ++index, val);
        }
    }

    /**
     * @Description: 节点类
     * @return
     * @author Administrator
     * @date 2021/7/17 20:21
     */
    private static class Node {
        /* key */
        private final byte key;
        /* val */
        private byte[] val;
        /* 下一元素指向 */
        private Map<Byte, Node> next;

        public Node(byte key) {
            this.key = key;
        }

        public Node(byte key, byte[] val) {
            this.key = key;
            this.val = val;
        }

        public byte getKey() {
            return key;
        }

        public byte[] getVal() {
            return val;
        }

        public void setVal(byte[] val) {
            this.val = val;
        }

        public Map<Byte, Node> getNext() {
            return next;
        }

        public void setNext(Map<Byte, Node> next) {
            this.next = next;
        }
    }
}
