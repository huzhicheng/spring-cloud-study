package kite.springcloud.jxm.dto;

import lombok.Data;

/**
 * SystemInfo
 *
 * @author fengzheng
 * @date 2019/8/8
 */
@Data
public class SystemInfo {

    /**
     * 操作系统打开的文件描述符数量
     */
    private long openFileDescriptorCount;

    /**
     * 操作系统最大文件描述符数量
     */
    private long maxFileDescriptorCount;

    /**
     * Returns the amount of virtual memory that is guaranteed to be available to the running process in bytes
     * , or -1 if this operation is not supported
     */
    private long committedVirtualMemorySize;

    /**
     * Returns the total amount of swap space in bytes
     */
    private long totalSwapSpaceSize;

    /**
     * Returns the amount of free swap space in bytes
     */
    private long freeSwapSpaceSize;

    /**
     * Returns the amount of free physical memory in bytes
     */
    private long freePhysicalMemorySize;

    /**
     * Returns the total amount of physical memory in bytes
     */
    private long totalPhysicalMemorySize;

    /**
     * Returns the CPU time used by the process on which the Java virtual machine is running in nanoseconds
     */
    private long processCpuTime;

    /**
     * returns the CPU usage of the whole system
     */
    private double systemCpuLoad;

    /**
     * returns the CPU usage of the JVM
     */
    private double processCpuLoad;

    /**
     * Returns the operating system name.
     * This method is equivalent to System.getProperty("os.name")
     */
    private String name;

    /**
     * Returns the number of processors available to the Java virtual machine
     */
    private int availableProcessors;

    /**
     * Returns the operating system architecture.
     * This method is equivalent to <tt>System.getProperty("os.arch")</tt>.
     */
    private String arch;
    /**
     * Returns the operating system version.
     * This method is equivalent to <tt>System.getProperty("os.version")</tt>.
     */
    private String version;

    /**
     * Returns the system load average for the last minute.
     */
    private double systemLoadAverage;


}
