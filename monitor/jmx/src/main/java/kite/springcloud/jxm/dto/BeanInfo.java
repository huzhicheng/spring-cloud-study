package kite.springcloud.jxm.dto;

import lombok.Data;

import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import java.util.List;

/**
 * BeanInfo
 *
 * @author fengzheng
 * @date 2019/8/5
 */
@Data
public class BeanInfo {

    private List<BeanAttributeInfo> beanAttributeInfos;

    private MBeanOperationInfo[] operationInfos;

    private MBeanNotificationInfo[] notificationInfos;
}
