package kite.springcloud.jxm.service;

import kite.springcloud.jxm.dto.*;
import kite.springcloud.jxm.dto.ThreadInfo;
import kite.springcloud.jxm.utils.EntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;
import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * MonitorDashboardService
 * 采集如下几个指标：
 * 1. 内存占用
 * 2. CPU 使用率
 * 3. 线程数量
 * 4. 类加载
 * 5. 操作系统信息 通过 OperatingSystemMXBean 或者直接查询 java.lang:type=OperatingSystem
 * 6. jvm 信息 通过 RuntimeMXBean 或者直接查询 java.lang:type=Runtime
 *
 * @author fengzheng
 * @date 2019/8/6
 */
@Service(value = "monitorDashboardService")
public class MonitorDashboardService {

    @Autowired
    private JmxService jmxService;

    public Overview overview() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        ObjectName systemBeanObjectName = operatingSystemMXBean.getObjectName();
        BeanInfo systemBean = jmxService.getObjectNameProperties(systemBeanObjectName.getCanonicalName());
        BeanInfo jvmBean = jmxService.getObjectNameProperties(runtimeMXBean.getObjectName().getCanonicalName());

        Overview overview = new Overview();
        SystemInfo systemInfo = buildSystemInfo(systemBean);
        JvmInfo jvmInfo = buildJvmInfo(jvmBean);
        overview.setSystemInfo(systemInfo);
        overview.setJvmInfo(jvmInfo);
        buildMemoryInfo(overview);
        MetaSpace metaSpace = buildMetaspace();
        overview.setMetaSpace(metaSpace);
        ThreadInfo threadInfo = buildThreadInfo();
        overview.setThreadInfo(threadInfo);

        ClassLoadingInfo classLoadingInfo = buildClassInfo();
        overview.setClassLoadingInfo(classLoadingInfo);

        List<GarbageInfo> garbageInfos = buildGarbageCollectorInfo();
        overview.setGarbageCollectorInfo(garbageInfos);
        return overview;

    }

    /**
     * 系统参数
     *
     * @param systemBean
     * @return
     */
    private SystemInfo buildSystemInfo(BeanInfo systemBean) {
        List<BeanAttributeInfo> attributeInfos = systemBean.getBeanAttributeInfos();
        SystemInfo systemInfo = new SystemInfo();
        for (BeanAttributeInfo beanAttributeInfo : attributeInfos) {
            String name = beanAttributeInfo.getName();
            String firstCharLowerCase = name.substring(0, 1).toLowerCase();
            name = name.replaceFirst("[A-Z]{1}", firstCharLowerCase);
            systemInfo = EntryUtil.setValue(systemInfo, name, beanAttributeInfo.getValue().getData());
        }
        return systemInfo;
    }

    /**
     * jvm 参数
     *
     * @param runtimeBean
     * @return
     */
    private JvmInfo buildJvmInfo(BeanInfo runtimeBean) {
        List<BeanAttributeInfo> attributeInfos = runtimeBean.getBeanAttributeInfos();
        JvmInfo jvmInfo = new JvmInfo();
        for (BeanAttributeInfo beanAttributeInfo : attributeInfos) {
            String name = beanAttributeInfo.getName();
            String firstCharLowerCase = name.substring(0, 1).toLowerCase();
            name = name.replaceFirst("[A-Z]{1}", firstCharLowerCase);
            if (!name.equals("systemProperties")) {
                jvmInfo = EntryUtil.setValue(jvmInfo, name, beanAttributeInfo.getValue().getData());
            } else {
                /**
                 * systemProperties 格式特殊 需要单独处理
                 */
                List<TreeMap<String, Object>> properties = (ArrayList) beanAttributeInfo.getValue().getData();
                jvmInfo.setSystemProperties(properties);
            }
        }
        return jvmInfo;
    }

    /**
     * jvm 内存信息 堆和非堆
     *
     * @param overview
     */
    private void buildMemoryInfo(Overview overview) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        overview.setHeapMemoryUsage(heapMemoryUsage);
        overview.setNonHeapMemoryUsage(nonHeapMemoryUsage);
    }

    /**
     * Metaspace 元空间信息
     *
     */
    private MetaSpace buildMetaspace() {
        MetaSpace metaSpace = new MetaSpace();
        BeanInfo beanInfo = jmxService.getObjectNameProperties("java.lang:name=Metaspace,type=MemoryPool");
        List<BeanAttributeInfo> beanAttributeInfos = beanInfo.getBeanAttributeInfos();
        for (BeanAttributeInfo attributeInfo : beanAttributeInfos) {
            if (attributeInfo.getName().equals("Usage")) {
                TreeMap<String, Object> usageMap = (TreeMap<String, Object>) attributeInfo.getValue().getData();
                metaSpace.setCommitted((long) usageMap.get("committed"));
                metaSpace.setInit((long) usageMap.get("init"));
                metaSpace.setMax((long) usageMap.get("max"));
                metaSpace.setUsed((long) usageMap.get("used"));
                return metaSpace;
            }
        }
        return null;
    }

    /**
     * 线程信息
     * @return
     */
    private ThreadInfo buildThreadInfo(){
        ThreadInfo threadInfo = new ThreadInfo();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        threadInfo.setLiveThreadCount(threadMXBean.getThreadCount());
        threadInfo.setDaemonThreadCount( threadMXBean.getDaemonThreadCount());
        threadInfo.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
        threadInfo.setLivePeakThreadCount(threadMXBean.getPeakThreadCount());
        return threadInfo;
    }

    /**
     * class 信息
     * @return
     */
    private ClassLoadingInfo buildClassInfo(){
        ClassLoadingInfo classLoadingInfo = new ClassLoadingInfo();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        classLoadingInfo.setTotalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount());
        classLoadingInfo.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());
        classLoadingInfo.setUnloadedClassCount(classLoadingMXBean.getUnloadedClassCount());
        return classLoadingInfo;
    }

    /**
     * 垃圾收集器信息
     */
    private List<GarbageInfo> buildGarbageCollectorInfo(){
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        List<GarbageInfo> garbageInfos = new ArrayList<>();
        for(GarbageCollectorMXBean garbageCollectorMXBean:garbageCollectorMXBeans){
            GarbageInfo garbageInfo = new GarbageInfo();
            garbageInfo.setName(garbageCollectorMXBean.getName());
            garbageInfo.setCollectionCount(garbageCollectorMXBean.getCollectionCount());
            garbageInfo.setMemoryPoolNames(garbageCollectorMXBean.getMemoryPoolNames());
            garbageInfos.add(garbageInfo);
        }
        return garbageInfos;
    }
}
