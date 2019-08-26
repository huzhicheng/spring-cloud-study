package kite.springcloud.jxm.dto;

import lombok.Data;

/**
 * ClassLoadingInfo
 *
 * @author fengzheng
 * @date 2019/8/16
 */
@Data
public class ClassLoadingInfo {

    private long totalLoadedClassCount;

    private int loadedClassCount;

    private long unloadedClassCount;

}
