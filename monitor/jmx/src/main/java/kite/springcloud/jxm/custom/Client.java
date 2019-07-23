package kite.springcloud.jxm.custom;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;


public class Client {
    public static void main(String[] args) throws IOException, Exception, NullPointerException {
        String jmxUrl = "service:jmx:rmi:///jndi/rmi://localhost:8999/jmxrmi";
        monitor(jmxUrl);
    }


    public static void monitor(String url) throws Exception{
        JMXServiceURL jmxServiceURL = new JMXServiceURL
                (url);
        JMXConnector jmxc = JMXConnectorFactory.connect(jmxServiceURL, null);

        MBeanServerConnection msc = jmxc.getMBeanServerConnection();
        String[] domains = msc.getDomains();
        for (String domain : domains) {
            System.out.println(domain);
            ObjectName queryObjectName = new ObjectName(domain + ":*");
            //ObjectName queryObjectName = new ObjectName(domain + ":name=customerUserBean,type=customer");
            Set<ObjectName> objectNames = msc.queryNames(queryObjectName, null);
            for (ObjectName objectName : objectNames) {
                System.out.println("    @@@" + objectName.getCanonicalName());

                MBeanInfo mBeanInfo = msc.getMBeanInfo(objectName);
                MBeanAttributeInfo[] mBeanAttributeInfos = mBeanInfo.getAttributes();
                for (MBeanAttributeInfo mBeanAttributeInfo : mBeanAttributeInfos) {
                    try {
                        Object attribute = mBeanAttributeInfo.isReadable()
                                ? msc.getAttribute(objectName, mBeanAttributeInfo.getName())
                                : "不可读";
                        if (mBeanAttributeInfo.getType().equals("javax.management.openmbean.CompositeData") && mBeanAttributeInfo.isReadable()) {
                            CompositeData compositeData = (CompositeData) attribute;
                            analyzeCompositeData(compositeData);

                        } else if (mBeanAttributeInfo.getType().equals("[Ljavax.management.openmbean.CompositeData;") && mBeanAttributeInfo.isReadable()) {
                            CompositeData[] compositeDataArray = (CompositeData[]) attribute;
                            for (CompositeData compositeData : compositeDataArray) {
                                analyzeCompositeData(compositeData);
                            }
                        } else {
                            System.out.println("        ###" + mBeanAttributeInfo.getName() + "(" + mBeanAttributeInfo.getType() + ")=" + attribute);
                        }
                    } catch (Exception e) {
                        //System.out.print("         " + mBeanAttributeInfo.getName() + " 异常：" + e.getMessage());
                        continue;
                    }
                }

//                for (MBeanOperationInfo oper : mBeanInfo.getOperations()) {
//                    System.out.println(oper.getName() + ":" + oper.getDescription());
//                }
//                System.out.println("[Notifications]");
//                for (MBeanNotificationInfo notice : mBeanInfo.getNotifications()) {
//                    System.out.println(notice.getName() + ":" + notice.getDescription());
//                }
            }
        }
    }

    static void analyzeCompositeData(CompositeData compositeData) throws Exception {
        System.out.println("        ***" + compositeData.getCompositeType().getTypeName());
        if (compositeData instanceof CompositeDataSupport) {
            CompositeDataSupport support = (CompositeDataSupport) compositeData;
            Field field = support.getClass().getDeclaredField("contents");
            field.setAccessible(true);
            Object map = field.get(support);
            SortedMap<String, Object> sortedMap = (SortedMap<String, Object>) map;
            Iterator iterator = sortedMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) next;

                if (entry.getValue() instanceof CompositeData) {
                    CompositeData compositeData1 = (CompositeData) entry.getValue();
                    analyzeCompositeData(compositeData1);
                } else if (entry.getValue() instanceof TabularDataSupport) {
                    TabularDataSupport tabularDataSupport = (TabularDataSupport) entry.getValue();
                    Iterator iterator1 = tabularDataSupport.entrySet().iterator();
                    while (iterator1.hasNext()){
                        Map.Entry<Object,Object> objectEntry = (Map.Entry<Object,Object>) iterator1.next();
                        System.out.println("            ~~~"+objectEntry.getKey());
                        Object o = objectEntry.getValue();
                        if(o instanceof CompositeData){
                            analyzeCompositeData((CompositeData)o);
                        }else{
                            System.out.println("                  " + o);
                        }
                    }
                } else {
//                    if (entry.getKey().equals("memoryUsageAfterGc")) {
//                        Object o = entry.getValue();
//                        System.out.println(o);
//                    }
                    System.out.println("            ---" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
    }
}