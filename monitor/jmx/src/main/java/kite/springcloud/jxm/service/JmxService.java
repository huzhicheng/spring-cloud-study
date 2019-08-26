package kite.springcloud.jxm.service;

import kite.springcloud.jxm.config.JmxConnectorInstance;
import kite.springcloud.jxm.dto.BeanAttributeInfo;
import kite.springcloud.jxm.dto.BeanAttributeValue;
import kite.springcloud.jxm.dto.BeanInfo;
import kite.springcloud.jxm.dto.Node;
import kite.springcloud.jxm.enums.NodeType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;
import javax.management.remote.JMXConnector;
import java.lang.reflect.Field;
import java.util.*;

/**
 * JmxService
 *
 * @author fengzheng
 * @date 2019/7/23
 */
@Service(value = "jmxService")
@Data
@Slf4j
public class JmxService {

    /**
     * 获取所有 domain
     *
     * @return
     * @throws Exception
     */
    public List<Node> getDomains() throws Exception {
        List<Node> nodes = new ArrayList<>();
        JmxConnectorInstance commonConfig = JmxConnectorInstance.INSTANCE;
        JMXConnector connector = commonConfig.getJmxConnector();
        if (connector != null) {
            MBeanServerConnection msc = connector.getMBeanServerConnection();
            String[] domains = msc.getDomains();
            for (String domain : domains) {
                Node node = new Node();
                node.setKey(domain);
                node.setTitle(domain);
                node.setNodeType(NodeType.DOMAIN.getName());
                List<Node> firstLevelObjectNames = getObjectNamesByDomain(msc, domain);
                node.setChildren(firstLevelObjectNames);
                nodes.add(node);
            }
        }
        return nodes;
    }

    public List<Node> getObjectNamesByDomain(MBeanServerConnection msc, String domain) throws Exception {
        List<Node> nodes = new ArrayList<>();
        ObjectName queryObjectName = new ObjectName(domain + ":*");
        Set<ObjectName> objectNames = msc.queryNames(queryObjectName, null);
        Map<String, ObjectName> types = new HashMap<>();
        for (ObjectName objectName : objectNames) {

            String type = objectName.getKeyProperty("type");
            types.put(type, objectName);
        }

        for (Map.Entry<String, ObjectName> entry : types.entrySet()) {
            Node node = new Node();
            node.setTitle(StringUtils.isEmpty(entry.getKey()) ? entry.getValue().getCanonicalName() : entry.getKey());
            node.setKey(entry.getValue().getCanonicalName());
            node.setNodeType(NodeType.OBJECTNAME.getName());
            node.setFullName(entry.getValue().getCanonicalName());
            List<Node> subNodes = getObjectNamesByType(msc, domain, entry.getKey());
            if (subNodes.size() > 1) {
                node.setChildren(subNodes);
            }
            nodes.add(node);
        }
        return nodes;
    }

    public BeanInfo getObjectNameProperties(String fullName) {
        JmxConnectorInstance commonConfig = JmxConnectorInstance.INSTANCE;
        JMXConnector connector = commonConfig.getJmxConnector();
        BeanInfo beanInfo = new BeanInfo();
        List<BeanAttributeInfo> beanAttributeInfos = new ArrayList<>();
        try {
            if (connector != null) {
                MBeanServerConnection msc = connector.getMBeanServerConnection();
                ObjectName queryObjectName = new ObjectName(fullName);
                Set<ObjectName> objectNames = msc.queryNames(queryObjectName, null);
                if (objectNames != null) {
                    for (ObjectName o : objectNames) {
                        MBeanInfo mBeanInfo = msc.getMBeanInfo(o);
                        MBeanAttributeInfo[] mBeanAttributeInfos = mBeanInfo.getAttributes();
                        MBeanOperationInfo[] mBeanOperationInfos = mBeanInfo.getOperations();

                        MBeanNotificationInfo[] mBeanNotificationInfos = mBeanInfo.getNotifications();

                        for (MBeanAttributeInfo mBeanAttributeInfo : mBeanAttributeInfos) {
                            boolean isReadable = mBeanAttributeInfo.isReadable();
                            Object attribute;
                            if (isReadable) {
                                try {
                                    attribute = msc.getAttribute(o, mBeanAttributeInfo.getName());
                                } catch (Exception e) {
                                    //log.error(mBeanAttributeInfo.getName() + ":" + e.getMessage());
                                    attribute = "不可用";
                                }
                            } else {
                                attribute = "不可读";
                            }
                            BeanAttributeInfo beanAttributeInfo = new BeanAttributeInfo();
                            beanAttributeInfo.setName(mBeanAttributeInfo.getName());

                            beanAttributeInfo.setAttributeInfo(mBeanAttributeInfo);

                            BeanAttributeValue beanAttributeValue = new BeanAttributeValue();
                            if (mBeanAttributeInfo.getType().equals("javax.management.openmbean.CompositeData") && mBeanAttributeInfo.isReadable()) {
                                SortedMap<String, Object> map = analyzeCompositeData(attribute);
                                beanAttributeValue.setCompositeData(true);
                                beanAttributeValue.setData(map);
                            } else if (mBeanAttributeInfo.getType().equals("[Ljavax.management.openmbean.CompositeData;") && mBeanAttributeInfo.isReadable()) {
                                List<SortedMap<String, Object>> mapList = new ArrayList<>();
                                CompositeData[] compositeDataArray = (CompositeData[]) attribute;
                                for (CompositeData compositeData : compositeDataArray) {
                                    SortedMap<String, Object> map = analyzeCompositeData(compositeData);
                                    mapList.add(map);
                                }
                                beanAttributeValue.setCompositeData(true);
                                beanAttributeValue.setData(mapList);
                            } else if (mBeanAttributeInfo.getType().equals("javax.management.openmbean.TabularData")) {
                                List<SortedMap<String, Object>> mapList = new ArrayList<>();
                                TabularDataSupport tabularDataSupport = (TabularDataSupport)  attribute;
                                for(Map.Entry<Object, Object> entry: tabularDataSupport.entrySet()){
                                    SortedMap<String, Object> map = analyzeCompositeData(entry.getValue());
                                    SortedMap<String, Object> normalMap = new TreeMap<>();
                                    String key =(String)map.getOrDefault("key","");
                                    Object value = map.getOrDefault("value","");
                                    normalMap.put("name",key);
                                    normalMap.put("value",value);
                                    mapList.add(normalMap);
                                }
                                beanAttributeValue.setCompositeData(true);
                                beanAttributeValue.setData(mapList);
                            } else {
                                beanAttributeValue.setCompositeData(false);
                                beanAttributeValue.setData(attribute);
                            }
                            beanAttributeInfo.setValue(beanAttributeValue);
                            beanAttributeInfos.add(beanAttributeInfo);
                        }
                        beanInfo.setOperationInfos(mBeanOperationInfos);
                        beanInfo.setNotificationInfos(mBeanNotificationInfos);
                    }
                    beanInfo.setBeanAttributeInfos(beanAttributeInfos);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanInfo;
    }

    public void invokeMethod(String fullName, String methodName) {
        JmxConnectorInstance commonConfig = JmxConnectorInstance.INSTANCE;
        JMXConnector connector = commonConfig.getJmxConnector();
        if (connector != null) {
            try {
                MBeanServerConnection msc = connector.getMBeanServerConnection();
                ObjectName queryObjectName = new ObjectName(fullName);
                Object o = msc.invoke(queryObjectName, methodName, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private List<Node> getObjectNamesByType(MBeanServerConnection msc, String domain, String type) {
        List<Node> nodes = new ArrayList<>();
        try {
            ObjectName queryObjectName = new ObjectName(domain + ":type=" + type + ",*");
            Set<ObjectName> objectNames = msc.queryNames(queryObjectName, null);
            for (ObjectName objectName : objectNames) {
                Node node = new Node();
                String fullName = objectName.getCanonicalName();
                String name = objectName.getKeyProperty("name");
                node.setTitle(StringUtils.isEmpty(name) ? objectName.getCanonicalName() : name);
                node.setFullName(fullName);
                node.setKey(fullName);
                node.setNodeType(NodeType.OBJECTNAME.getName());
                nodes.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }


    private SortedMap<String, Object> analyzeCompositeData(Object compositeData) {
        try {
            if (compositeData instanceof CompositeDataSupport) {
                CompositeDataSupport support = (CompositeDataSupport) compositeData;
                Field field = support.getClass().getDeclaredField("contents");
                field.setAccessible(true);
                Object map = field.get(support);
                SortedMap<String, Object> sortedMap = (SortedMap<String, Object>) map;

                return sortedMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
