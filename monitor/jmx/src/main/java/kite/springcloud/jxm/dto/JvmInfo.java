package kite.springcloud.jxm.dto;

import lombok.Data;

import java.util.List;
import java.util.TreeMap;

/**
 * JvmInfo
 *
 * @author fengzheng
 * @date 2019/8/8
 */
@Data
public class JvmInfo {

    private String name;

    private String classPath;

    private long startTime;

    private String specName;

    private String specVendor;

    private String specVersion;

    private String managementSpecVersion;

    private String[] inputArguments;

    /**
     * 67 个系统参数
     */
    private List<TreeMap<String,Object>> systemProperties;

    private String vmName;

    private String vmVendor;

    private String vmVersion;

    private String libraryPath;

    private long uptime;
}
