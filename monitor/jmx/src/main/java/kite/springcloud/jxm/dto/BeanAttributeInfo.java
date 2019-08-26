package kite.springcloud.jxm.dto;

import lombok.Data;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;

/**
 * BeanAttributeInfo
 *
 * @author fengzheng
 * @date 2019/8/1
 */
@Data
public class BeanAttributeInfo {

    private String name;

    private BeanAttributeValue value;

    private MBeanAttributeInfo attributeInfo;



}
