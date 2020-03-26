package com.yzm.common.util;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:52 2019/12/24
 * ===========================
 */
public class CpuUtil {

    private static CpuUtil instance = new CpuUtil();

    private OperatingSystemMXBean osMxBean;
    private ThreadMXBean threadBean;
    private long preTime = System.nanoTime();
    private long preUsedTime = 0;

    private CpuUtil() {
        osMxBean = ManagementFactory.getOperatingSystemMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
    }

    public static CpuUtil getInstance() {
        return instance;
    }

    public String getProcessCpu() {
        long totalTime = 0;
        for (long id : threadBean.getAllThreadIds()) {
            totalTime += threadBean.getThreadCpuTime(id);
        }
        long curtime = System.nanoTime();
        long usedTime = totalTime - preUsedTime;
        long totalPassedTime = curtime - preTime;
        preTime = curtime;
        preUsedTime = totalTime;
        double v = (((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors()) * 100;
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(v);
    }
}
