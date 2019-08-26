package kite.springcloud.jxm.dto;

import lombok.Data;

/**
 * MetaSpace
 *
 * @author fengzheng
 * @date 2019/8/16
 */
@Data
public class MetaSpace {

    private String unit = "Byte";

    private long committed;

    private long init;

    private long max;

    private long used;
}
