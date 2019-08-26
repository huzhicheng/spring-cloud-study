package kite.springcloud.jxm.dto;

import lombok.Data;

/**
 * GarbageInfo
 *
 * @author fengzheng
 * @date 2019/8/16
 */
@Data
public class GarbageInfo {

    private String name;

    private long collectionCount;

    private String[] memoryPoolNames;
}
