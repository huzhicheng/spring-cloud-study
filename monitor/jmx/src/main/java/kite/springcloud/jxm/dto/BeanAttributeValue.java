package kite.springcloud.jxm.dto;

import lombok.Data;

/**
 * BeanAttributeValue
 *
 * @author fengzheng
 * @date 2019/8/1
 */
@Data
public class BeanAttributeValue {

    private boolean isCompositeData;

    private Object data;
}
