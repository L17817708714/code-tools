package com.utils;

import com.sun.deploy.util.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @className: SnowFlake
 * @description: 雪花算法发号器
 * SnowFlake产生的ID是一个64位的整型，结构如下：
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1位：标识部分，在java中由于long的最高位是符号位，正数是0，负数是1，一般生成的ID为正数，所以为0；
 * 41位：时间戳部分，这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（
 * 当前时间-固定的开始时间），这样可以使产生的ID从更小值开始；41位的时间戳可以使用69年，
 * (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；）
 * 10位：前5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点；
 * 12位：序列号部分，支持同一毫秒内同一个节点可以生成4096个ID；
 * @date 2021/7/22 19:03
 * @version：1.0
 */
public class SnowFlake {
    /**
     * 起始的时间戳
     */
    public final long START_TIMESTAMP = 1626970112547L;

    public final int dataCenterId;  //数据中心
    public final int machineId;     //机器标识

    /**
     * 每一部分占用位数
     */
    private final static int SEQUENCE_BIT = 12;   //序列号占用的位数
    private final static int MACHINE_BIT = 5;     //机器标识占用的位数
    private final static int DATA_CENTER_BIT = 5; //数据中心占用的位数
    /**
     * 每一部分最大值
     */
    private static final int MAX_DATA_CENTER_NUM = ~(-1 << DATA_CENTER_BIT);
    private static final int MAX_MACHINE_NUM = ~(-1 << MACHINE_BIT);
    private static final int MAX_SEQUENCE_NUM = ~(-1 << SEQUENCE_BIT);
    /**
     * 每一部分向左偏移位数
     */
    private static final int MACHINE_LEFT = SEQUENCE_BIT;
    private static final int DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final int TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long globalId; // 部分全局id
    private volatile int sequence; // 序列号
    private volatile long lastTimeStamp = 0L; // 上一次时间戳

    /**
     * @Description: 构造方法
     * @param dataCenterId: 数据中心标识
     * @param machineId: 机器标识
     * @param machineId: 开始的时间戳
     * @return 
     * @author Administrator
     * @date 2021/7/22 20:00
     */
    public SnowFlake(int dataCenterId, int machineId) throws IllegalAccessException {
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_NUM) {
            throw new IllegalAccessException("数据中心标识不能小于0或不能大于" + MAX_DATA_CENTER_NUM);
        }
        if (machineId < 0 || machineId > MAX_MACHINE_NUM) {
            throw new IllegalAccessException("机器码标识不能小于0或不能大于" + MAX_MACHINE_NUM);
        }

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
        this.globalId = (dataCenterId << DATA_CENTER_LEFT) | (machineId << MACHINE_LEFT);
    }

    /**
     * @Description: 生成下一个全局id
     * @return long
     * @author Administrator
     * @date 2021/7/22 20:52
     */
    public long nextId() {
        long nowTimeStamp;
        int nowSequence;
        synchronized(this) {
            nowTimeStamp = getNowTimeStamp();
            if (nowTimeStamp == lastTimeStamp) {
                // 相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
                if (sequence == 0) {
                    // 序列号已达最大，则自旋到下一毫秒
                    nowTimeStamp = getNextMill();
                }
            }else {
                // 不同毫秒，序列号设置为 0；
                sequence = 0;
            }
            lastTimeStamp = nowTimeStamp;
            nowSequence = sequence;
        }

        return globalId | ((nowTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT) | nowSequence;
    }

    /**
     * @Description: 当前时间自旋到下一毫秒
     * @return long
     * @author Administrator
     * @date 2021/7/22 23:33
     */
    public long getNextMill() {
        long nowTimeStamp = getNowTimeStamp();
        while (nowTimeStamp <= lastTimeStamp) {
            nowTimeStamp = getNowTimeStamp();
        }
        return nowTimeStamp;
    }

    /**
     * @Description: 获取当前时间戳
     * @return long
     * @author Administrator
     * @date 2021/7/22 23:38
     */
    public long getNowTimeStamp() {
        return System.currentTimeMillis();
    }

}
