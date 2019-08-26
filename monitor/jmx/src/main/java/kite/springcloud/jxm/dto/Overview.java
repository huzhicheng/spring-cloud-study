package kite.springcloud.jxm.dto;

import lombok.Data;

import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * Overview
 *
 * @author fengzheng
 * @date 2019/8/8
 */
@Data
public class Overview {

    private SystemInfo systemInfo;

    private JvmInfo jvmInfo;

    private MemoryUsage heapMemoryUsage;

    private MemoryUsage nonHeapMemoryUsage;

    private MetaSpace metaSpace;

    private ThreadInfo threadInfo;

    private ClassLoadingInfo classLoadingInfo;

    private List<GarbageInfo> garbageCollectorInfo;
}
